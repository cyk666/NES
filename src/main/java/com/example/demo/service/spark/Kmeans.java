package com.example.demo.service.spark;

import com.example.demo.domain.Node;
import org.springframework.stereotype.Service;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.rdd.RDD;
import scala.Tuple2;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author zdx
 * K-means聚类算法  算法入口 run(List<Node> nodes, int k)，nodes节点列表，k聚类数目
 * 在main函数中进行了测试，nodelist()生成测试用的nodelist
 */
@Service
public class Kmeans {

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

    public static double dis_vec(Vector v1, Vector v2){
        double[] vectorDouble1 = v1.toArray();
        double[] vectorDouble2 = v2.toArray();
        double sum=0;
        for(int i=0; i<vectorDouble1.length;i++){
            sum = sum + (vectorDouble1[i]-vectorDouble2[i])*(vectorDouble1[i]-vectorDouble2[i]);
        }
        return sum;
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

    public static void k_means(JavaRDD<String> rawData, int k){

        long count= 0;

        String str = rawData.first();
        String[] firstline = str.split(",");
        Random r = new Random(1);
        int[] ran = new int[k];
        for(int i=0 ; i<k ;  i++)
        {
            int ran1 = r.nextInt(300);
            ran[i] = ran1;
        }
        Arrays.sort(ran);
        double[] meanDouble = new double[firstline.length-2];
        JavaRDD<Tuple2<String, Vector>> labelsAndData = rawData.map(line -> {
            String[] lineArrya = line.split(",");
            double[] vectorDouble = new double[lineArrya.length-2];
            for (int i = 0, j=0; i < lineArrya.length; i++) {
                if(i==0 || i==lineArrya.length-1) {
                    continue;
                }
                vectorDouble[j] = Double.parseDouble(lineArrya[i]);
                j++;
            }
            String label = lineArrya[lineArrya.length-1];
            Vector vector = Vectors.dense(vectorDouble);
            return new Tuple2<String, Vector>(label,vector);
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

        //标准化
        JavaRDD<Tuple2<String, Vector>> labelsAndData_mean = labelsAndData.map(line -> {
            String lb = line._1;
            double[] vectorDouble = line._2.toArray();
            for (int i = 0; i < vectorDouble.length; i++) {
                vectorDouble[i] = vectorDouble[i]-meanDouble[i];
            }
            Vector vector = Vectors.dense(vectorDouble);
            return new Tuple2<String, Vector>(lb,vector);
        });
        JavaRDD<Vector> Data_mean_vector = labelsAndData_mean.map(f -> f._2);
        List< Vector> centerList = Data_mean_vector.takeSample(false,k,100);
        List<Tuple2<Integer, Vector>> center_k = new ArrayList<Tuple2<Integer, Vector>>();
        int c_k =1;
        for (Vector v : centerList) {
            center_k.add(new Tuple2<Integer, Vector>(c_k,v));
            c_k++;
        }
        JavaRDD<Vector> data0 = labelsAndData_mean.map(f -> f._2);
        for(int i=0; i<30;i++){

            JavaRDD<Tuple2<Integer, Vector>> data = data0.map(line -> {
                Tuple2<Integer, Vector>tup = center_k.get(0);
                Vector v = tup._2();
                int tag =0;
                double dis = dis_vec(v,line);
                for (Tuple2<Integer, Vector> cen : center_k) {
                    Vector v1 = cen._2();
                    double curdis= dis_vec(v1,line);
                    if(curdis<dis){
                        dis=curdis;
                        tag = cen._1();
                    }
                }
                return new Tuple2<Integer, Vector>(tag,line);
            });


            JavaPairRDD<Integer, Vector> data1= data.mapToPair(line->line);
            JavaPairRDD<Integer, Iterable<Vector>> data2 = data1.groupByKey();
            List<Tuple2<Integer, Vector>> data3 = data2.map(new Function<Tuple2<Integer, Iterable<Vector>>,Tuple2<Integer, Vector>>() {
                @Override
                public Tuple2<Integer, Vector> call(Tuple2<Integer, Iterable<Vector>> v2) throws Exception {
                    int lable = v2._1();
                    Iterator<Vector> ite = v2._2.iterator();
                    int count = 1;
                    Vector vec = ite.next();
                    double[] sum = vec.toArray();
                    while(ite.hasNext()){
                        Vector v0 = ite.next();
                        double[] doublevec = v0.toArray();
                        for(int i=0;i<sum.length;i++){
                            sum[i] += doublevec[i];
                        }
                        count++;
                    }
                    for(int i=0 ;i<sum.length;i++){
                        sum[i]=sum[i]/count;
                    }

                    Vector vector = Vectors.dense(sum);
                    return new Tuple2<Integer, Vector>(lable,vector);
                }
            }).collect();
            if(center_k.size()>data3.size()){
                int j;
                for( j=0;j<data3.size();j++){
                    center_k.set(j,data3.get(j));
                }
                while(j<center_k.size()){
                    center_k.remove(j);
                }
            }
            else{
                int j;
                for( j=0;j<center_k.size();j++){
                    center_k.set(j,data3.get(j));
                }
                while(j<data3.size()){
                    center_k.add(data3.get(j));
                }
            }

        }
        ArrayList<Map.Entry<Tuple2<Integer, String>, Long>> clusterLabelCount = new ArrayList<Map.Entry<Tuple2<Integer, String>, Long>>(labelsAndData.map(v -> {
            Vector ve = v._2();
            int tag =0;
            Tuple2<Integer, Vector>tup = center_k.get(0);
            double dis = dis_vec(ve,tup._2());
            for (Tuple2<Integer, Vector> cen : center_k) {
                Vector v1 = cen._2();
                double curdis= dis_vec(v1,ve);
                if(curdis<dis){
                    dis=curdis;
                    tag = cen._1();
                }
            }
            return new Tuple2<Integer, String>(tag, v._1);
        }).countByValue().entrySet());
        Collections.sort(clusterLabelCount, (m1, m2) -> m2.getKey()._1-m1.getKey()._1);
        clusterLabelCount.forEach(t -> System.out.println(t.getKey()._1 +"\t"+ t.getKey()._2 +"\t\t"+ t.getValue()));

        RDD<Vector> data = JavaRDD.toRDD(labelsAndData_mean.map(f -> f._2));

    }

    public static void run(List<Node> nodes, int k){
        List<String> list = new ArrayList<String>();
        Iterator<Node> it = nodes.iterator();
        while (it.hasNext()) {
            String s = node_string(it.next());
            list.add(s);
        }
        // 创建sparkconf对象

        SparkConf conf = new SparkConf().setAppName("kmean").setMaster("local[3]");
        // 创建javaSpark上下文对象
        JavaSparkContext jsc = new JavaSparkContext(conf);
        JavaRDD<String> rawData = jsc.parallelize(list);
        k_means(rawData,2);

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
        List<Node> list = nodelist();
        run(list,2);


    }

}
