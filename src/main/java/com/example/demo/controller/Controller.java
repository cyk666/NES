package com.example.demo.controller;

import com.example.demo.domain.Node;
import com.example.demo.service.dataCUD.CreateService;
import com.example.demo.service.dataCUD.CreateServiceImpl;
import com.example.demo.service.dataR.RetrieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cyk
 * 该模块是响应前端的调用。
 */

@RestController
@RequestMapping("/")
public class Controller {

    @Autowired
    RetrieveService retrieveService;


    CreateServiceImpl createServiceImpl;

    //测试方法，相应浏览器页面发出的请求。如果正确地在浏览器页面上显示结果，从neo4j检索流程正确。
    @GetMapping("/neo4j/{ip}")
    public Node findByIp(@PathVariable("ip") String ip) {
        return retrieveService.findByIp(ip);
    }

    @GetMapping("/firstcreate")
    public String firstCreate(){
        createServiceImpl = new CreateServiceImpl();
        createServiceImpl.firstCreate();
        return "first create success";
    }
    @GetMapping("/secondcreate")
    public String secondCreate(){
        createServiceImpl = new CreateServiceImpl();
        createServiceImpl.secondCreate();
        return "second create success";
    }

}
