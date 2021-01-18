package com.example.demo.service.dataCUD;

import java.io.IOException;

/**
 * @author wsf
 * 完成数据插入方法。
 */
public class CreateServiceImpl implements CreateService{

    @Override
    public void firstCreate() {
        try {
            Process proc1 =Runtime.getRuntime().exec("C:/Users/jiangshouhe/anaconda3/python.exe C:/Users/jiangshouhe/Desktop/实验室/NES/src/main/java/com/example/demo/service/dataCUD/loaddata_v2.py 0");
            int i=proc1.waitFor();
            System.out.println(i);
            Process proc2 =Runtime.getRuntime().exec("C:/Users/jiangshouhe/anaconda3/python.exe C:/Users/jiangshouhe/Desktop/实验室/NES/src/main/java/com/example/demo/service/dataCUD/loaddata_v3.py 0");
            proc2.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void secondCreate() {
        try {
            Process proc1 =Runtime.getRuntime().exec("C:/Users/jiangshouhe/anaconda3/python.exe C:/Users/jiangshouhe/Desktop/实验室/NES/src/main/java/com/example/demo/service/dataCUD/loaddata_v2.py 1");
            proc1.waitFor();
            Process proc2 =Runtime.getRuntime().exec("C:/Users/jiangshouhe/anaconda3/python.exe C:/Users/jiangshouhe/Desktop/实验室/NES/src/main/java/com/example/demo/service/dataCUD/loaddata_v3.py 1");
            proc2.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
