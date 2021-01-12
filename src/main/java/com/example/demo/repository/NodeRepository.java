package com.example.demo.repository;

import com.example.demo.domain.Node;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author cyk
 * 继承spring-data-neo4j提供的接口。对于neo4j中节点的简单CRUD方法定义在这里。
 */public interface NodeRepository extends Neo4jRepository<Node, Long> {

     //测试用的方法，根据输入的属性ip来从neo4j中检索节点，返回结果正确则neo4j配置无误。
     Node findByIp(@Param("ip") String ip);
}
