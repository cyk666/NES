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
            Process proc1 =Runtime.getRuntime().exec("C:/Users/jiangshouhe/anaconda3/python.exe C:/Users/jiangshouhe/Desktop/实验室/NES/src/main/java/com/example/demo/service/dataCUD/importdata.py 0");
            proc1.waitFor();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void secondCreate() {
        try {
            Process proc1 =Runtime.getRuntime().exec("C:/Users/jiangshouhe/anaconda3/python.exe C:/Users/jiangshouhe/Desktop/实验室/NES/src/main/java/com/example/demo/service/dataCUD/importdata.py 1");
            proc1.waitFor();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
