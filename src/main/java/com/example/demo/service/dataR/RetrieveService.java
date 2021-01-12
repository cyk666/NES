package com.example.demo.service.dataR;

import com.example.demo.domain.Node;

/**
 * @author cyk
 * 数据检索的方法在这里定义。
 */
public interface RetrieveService {

    //测试方法，在Service层定义一个方法：从neo4j中根据Ip查找一个节点。
    Node findByIp(String ip);
}
