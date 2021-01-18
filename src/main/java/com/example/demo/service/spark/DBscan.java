package com.example.demo.service.spark;

import com.example.demo.domain.Node;
import org.springframework.stereotype.Service;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.rdd.RDD;
import scala.Tuple2;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author zdx
 * DBscan算法   算法入口 run(List<Node> nodes)，nodes节点列表
 * 在main函数中进行了测试，nodelist()生成测试用的nodelist
 */
@Service
public class DBscan {
    public static List<String> importCsv(File file){
        List<String> dataList=new ArrayList<String>();

        BufferedReader br=null;
        int k =0;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                if(k == 0){
                    k++;
                    continue;
                }
                dataList.add(line);
            }
        }catch (Exception e) {
        }finally{
            if(br!=null){
                try {
                    br.close();
                    br=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return dataList;
    }

    public static double dis_vec(Vector v1,Vector v2){
        double[] vectorDouble1 = v1.toArray();
        double[] vectorDouble2 = v2.toArray();
        double sum=0;
        for(int i=0; i<vectorDouble1.length-1;i++){
            sum = sum + (vectorDouble1[i]-vectorDouble2[i])*(vectorDouble1[i]-vectorDouble2[i]);
        }
        return sum;
    }

    public static int count_0(List<Integer> dataList){
        int i=0;
        int k=-1;
        Iterator<Integer> it = dataList.iterator();
        while (it.hasNext()) {
            if(it.next()==0){
                k=i;
            }
            i++;
        }
        return k;
    }

    public static int count_1(List<Integer> dataList){
        int i=0;
        int k=-1;
        Iterator<Integer> it = dataList.iterator();
        while (it.hasNext()) {
            if(it.next()==1){
                k=i;
            }
            i++;
        }
        return k;
    }

    public static void DBscans(List<String> list2,List<String> dataList){
        // 创建sparkconf对象
        SparkConf conf = new SparkConf().setAppName("DBscan").setMaster("local[3]");
        // 创建javaSpark上下文对象
        JavaSparkContext jsc = new JavaSparkContext(conf);
        // 读取数据文本生成rdd
        long count = 0;
        int min_dis =100;
        JavaRDD<String> rawData = jsc.parallelize(list2);
        String str = rawData.first();
        String[] firstline = str.split(",");
        List<Integer> Lable = new ArrayList<Integer>();
        List<Integer> vist = new ArrayList<Integer>();
        List<Double> Lable_train = new ArrayList<Double>();
        double[] meanDouble = new double[firstline.length-2];
        Iterator<String> it = dataList.iterator();
        while (it.hasNext()) {
            String[] lineArrya = it.next().split(",");
            Lable.add(0);
            vist.add(0);
            Lable_train.add(Double.parseDouble(lineArrya[lineArrya.length-1]));
        }
        JavaRDD<Tuple2<Integer, Vector>> labelsAndData = rawData.map(line -> {
            String[] lineArrya = line.split(",");
            double[] vectorDouble = new double[lineArrya.length-2];
            int j = 0;
            for (int i = 0; i < lineArrya.length; i++) {
                if(i==0 || i == lineArrya.length-2) {
                    continue;
                }
                vectorDouble[j] = Double.parseDouble(lineArrya[i]);
                j++;
            }

            int label = 0;
            Vector vector = Vectors.dense(vectorDouble);
            return new Tuple2<Integer, Vector>(label,vector);
        });
        JavaRDD<Vector> labelsAndDatavector = labelsAndData.map(f -> f._2);
        Vector meanvector = labelsAndDatavector.reduce(new Function2<Vector,Vector,Vector>() {
            @Override
            public Vector call(Vector t1, Vector t2) throws Exception {
                double[] vectort1 = t1.toArray();
                double[] vectort2 = t2.toArray();
                for(int i=0; i<vectort1.length;i++){
                    vectort1[i] = vectort1[i] +vectort2[i] ;
                }
                Vector vector = Vectors.dense(vectort1);
                return vector;
            }


        });
        count = labelsAndData.count();
        double[] vmean = meanvector.toArray();

        for(int i=0; i<meanDouble.length;i++){
            meanDouble[i]=vmean[i]/count;
            System.out.println(meanDouble[i]);
        }
        meanDouble[meanDouble.length-1]=0;

        //标准化
        JavaRDD<Tuple2<Integer, Vector>> labelsAndData_mean = labelsAndData.map(line -> {
            int lb = line._1;
            double[] vectorDouble = line._2.toArray();
            for (int i = 0; i < vectorDouble.length; i++) {
                vectorDouble[i] = vectorDouble[i]-meanDouble[i];
            }
            Vector vector = Vectors.dense(vectorDouble);
            return new Tuple2<Integer, Vector>(lb,vector);
        });
        int lable_count =0;


        do{
            if (count_1(vist)!= -1){
                int cen = count_1(vist);
                String[] lineA = dataList.get(cen).split(",");
                double[] vecDouble = new double[lineA.length-2];
                int j = 0;
                for (int i = 0; i < lineA.length; i++) {
                    if(i==0 || i == lineA.length-1) {
                        continue;
                    }
                    vecDouble[j] = Double.parseDouble(lineA[i]);
                    vecDouble[j] = vecDouble[j]-meanDouble[j];
                    j++;
                }
                Vector vec = Vectors.dense(vecDouble);
                JavaRDD<Tuple2<Integer, Vector>> data_2 = labelsAndData_mean.map(line -> {
                    int lb = line._1;
                    Vector vector = line._2;
                    if(dis_vec(vec,vector)<min_dis){
                        lb = 1;
                    }
                    return new Tuple2<Integer, Vector>(lb,vector);
                });
                JavaRDD<Tuple2<Integer, Vector>> data_3 = data_2.filter(new Function<Tuple2<Integer, Vector>, Boolean>() {
                    @Override
                    public Boolean call(Tuple2<Integer, Vector> v1) throws Exception {
                        return v1._1==1;
                    }
                });
                if(data_3.count()>5){
                    vist.set(cen,3);
                    Lable.set(cen,lable_count);
                    int biaoqian = lable_count;
                    List<Integer> list_tmp2 = data_3.map(new Function<Tuple2<Integer, Vector>,Integer>() {
                        @Override
                        public Integer call(Tuple2<Integer, Vector> v2) throws Exception {
                            Vector vec = v2._2;
                            double[] vectorDouble1 = vec.toArray();
                            int tmp = (int) vectorDouble1[vectorDouble1.length-1];

                            return tmp;
                        }
                    }).collect();
                    Iterator<Integer> itetmp = list_tmp2.iterator();
                    while (itetmp.hasNext()) {
                        int l = itetmp.next();
                        if(vist.get(l)==0){
                            vist.set(l,1);
                            System.out.println(vist.get(l));
                            Lable.set(l,biaoqian);
                        }

                    }

                }
                else{
                    vist.set(cen,2);
                }

            }
            else{
                if(count_0(vist)!=-1){
                    lable_count++;
                    String[] lineA = dataList.get(count_0(vist)).split(",");
                    int center = count_0(vist);
                    double[] vecDouble = new double[lineA.length-2];
                    int j = 0;
                    for (int i = 0; i < lineA.length; i++) {
                        if(i==0 || i == lineA.length-1) {
                            continue;
                        }
                        vecDouble[j] = Double.parseDouble(lineA[i]);
                        vecDouble[j] = vecDouble[j]-meanDouble[j];
                        j++;
                    }
                    Vector vec = Vectors.dense(vecDouble);
                    JavaRDD<Tuple2<Integer, Vector>> data_0 = labelsAndData_mean.map(line -> {
                        int lb = line._1;
                        Vector vector = line._2;
                        if(dis_vec(vec,vector)<min_dis){
                            lb = 1;
                        }
                        return new Tuple2<Integer, Vector>(lb,vector);
                    });
                    JavaRDD<Tuple2<Integer, Vector>> data_1 = data_0.filter(new Function<Tuple2<Integer, Vector>, Boolean>() {
                        @Override
                        public Boolean call(Tuple2<Integer, Vector> v1) throws Exception {
                            return v1._1==1;
                        }
                    });
                    if(data_1.count()>5){
                        vist.set(center,3);
                        Lable.set(center,lable_count);
                        int biaoqian = lable_count;
                        List<Integer> list_tmp = data_1.map(new Function<Tuple2<Integer, Vector>,Integer>() {
                            @Override
                            public Integer call(Tuple2<Integer, Vector> v2) throws Exception {
                                Vector vec = v2._2;
                                double[] vectorDouble1 = vec.toArray();
                                int tmp = (int) vectorDouble1[vectorDouble1.length-1];

                                return tmp;
                            }
                        }).collect();
                        Iterator<Integer> itetmp = list_tmp.iterator();
                        while (itetmp.hasNext()) {
                            int l = itetmp.next();
                            if(vist.get(l)==0){
                                vist.set(l,1);
                                System.out.println(vist.get(l));
                                Lable.set(l,biaoqian);
                            }
                        }


                    }
                    else{
                        vist.set(center,-1);
                        lable_count--;
                    }

                }
            }
            System.out.println(count_1(vist));



        }while( count_0(vist)!=-1||count_1(vist)!=-1);
        List<Tuple2<Integer, Double>> resultlist = new ArrayList<Tuple2<Integer, Double>>();
        Iterator<Integer> ite = Lable.iterator();
        int result_i =0;
        while (ite.hasNext()) {
            int l = ite.next();
            Double lt = Lable_train.get(result_i);
            Tuple2<Integer, Double> tupres = new Tuple2<Integer, Double>(l,lt);
            resultlist.add(tupres);
            result_i++;
        }
        JavaRDD<Tuple2<Integer, Double>> res_data = jsc.parallelize(resultlist);
        ArrayList<Map.Entry<Tuple2<Integer, Double>, Long>> clusterLabelCount = new ArrayList<Map.Entry<Tuple2<Integer, Double>, Long>>(res_data.map(v -> {


            return new Tuple2<Integer, Double>(v._1, v._2);
        }).countByValue().entrySet());
        Collections.sort(clusterLabelCount, (m1, m2) -> m2.getKey()._1-m1.getKey()._1);
        clusterLabelCount.forEach(t -> System.out.println(t.getKey()._1 +"\t"+ t.getKey()._2 +"\t\t"+ t.getValue()));

    }

    public static String node_string(Node node){
        return node.getIp()+","+node.getInd()+","
                +node.getOd()+","
                +node.getIdW()+","
                +node.getOdw()+","
                +node.getCloseness()+","
                +node.getBetweenness()+","
                +node.getEigenVector()+","
                +node.getAveNeighborDegree()+","
                +node.getLcc()+","
                +node.getLabel();
    }

    public static void run(List<Node> nodes){
        List<String> list = new ArrayList<String>();
        Iterator<Node> it = nodes.iterator();
        while (it.hasNext()) {
            String s = node_string(it.next());
            list.add(s);
        }
        List<String> list2 = new ArrayList<String>();
        Iterator<String> ite = list.iterator();
        int i=0;
        while (ite.hasNext()) {
            String s = ite.next()+","+i;
            list2.add(s);
            i++;

        }
        DBscans(list2,list);


    }

    public static List<Node> nodelist(){
        String ip="1111";
        long id = 1;
        String lable1 = "1";
        String lable2 = "2";
        List<Node> list =new ArrayList<Node>();
        for(int i=0;i<10;i++){
            long ind = 10+i;
            long od = 10+i;
            long idw = 20+i;
            long odw = 20+i;
            double closeness = 10.0+i;
            double betweenness = 10.0+i;
            double eigenVector = 5.0+i;
            double aveNeighborDegree = 15.0+i;
            double lcc = 21.0+i;
            Node node = new Node(id,ip,ind,od,idw,odw,closeness,betweenness,eigenVector,aveNeighborDegree,lcc,lable1);
            list.add(node);
        }
        for(int i=0;i<10;i++){
            long ind = 100+i;
            long od = 100+i;
            long idw = 200+i;
            long odw = 200+i;
            double closeness = 10.00+i;
            double betweenness = 100.0+i;
            double eigenVector = 50.0+i;
            double aveNeighborDegree = 150.0+i;
            double lcc = 210.0+i;
            Node node = new Node(id,ip,ind,od,idw,odw,closeness,betweenness,eigenVector,aveNeighborDegree,lcc,lable2);
            list.add(node);
        }
        return list;
    }





    public static void main(String[] args) {
        List<Node> nodes = nodelist();
        run(nodes);



    }
}
