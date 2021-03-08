package com.example.demo.neo4j.repository;

import com.example.demo.neo4j.pojo.Node;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author cyk
 * 继承spring-data-neo4j提供的接口。对于neo4j中节点的简单CRUD方法定义在这里。
 */public interface NodeRepository extends Neo4jRepository<Node, Long> {

     /**
      * 测试用的方法，根据输入的属性ip来从neo4j中检索节点，返回结果正确则neo4j配置无误。
      * @param ip 节点的 ip
      * @return 返回一个 Node 节点
      */
     Node findByIp(@Param("ip") String ip);

     @Query("MATCH(n:IP) RETURN n")
     List<Node> findAllNodes();
}
