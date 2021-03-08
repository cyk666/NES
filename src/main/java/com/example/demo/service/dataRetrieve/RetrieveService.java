package com.example.demo.service.dataRetrieve;

import com.example.demo.elasticsearch.pojo.EsEdge;
import com.example.demo.neo4j.pojo.Edge;
import com.example.demo.neo4j.pojo.Node;
import com.example.demo.utils.IpQuintuple;

import java.io.IOException;
import java.util.List;

/**
 * @author cyk
 * 数据检索的方法在这里定义。
 */
public interface RetrieveService {

    /**
     * 从neo4j中根据Ip查找一个节点。
     * @param ip
     */
    Node findNodeByIp(String ip);

    //检索所有neo4j中的节点
    List<Node> findAllNodes();

    //检索 neo4j 中的一条边
    List<Edge> findEdgeByIp(String ip);

    //检索所有neo4j中的边
    List<Edge> findAllEdges();

    //检索一个五元组
    List<IpQuintuple> getIpQuintuple(List<Edge> edgeList) throws IOException;

    //检索所有带属性的边,方法重载
    List<EsEdge> getFieldEdges(Edge edge, String index) throws IOException;

    List<EsEdge> getFieldEdges(List<Edge> edgeList, String index) throws IOException;

}
