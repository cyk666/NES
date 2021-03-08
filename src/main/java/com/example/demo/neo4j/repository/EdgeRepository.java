package com.example.demo.neo4j.repository;

import com.example.demo.neo4j.pojo.Edge;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

import static com.example.demo.constant.Constant.NODE_INDEX;

/**
 * @author cyk
 * 继承spring-data-neo4j提供的接口。对于neo4j中边的简单CRUD方法定义在这里。
 */
public interface EdgeRepository extends Neo4jRepository<Edge, Long> {
    @Query("MATCH (n:IP)<-[r:FLOW]->(m:IP) WHERE n.id = $ip RETURN r, n, m")
    List<Edge> findEdgeByIp(String ip);


    @Query("MATCH (n:IP)<-[r:FLOW]->(m:IP) RETURN r, n, m")
    List<Edge> findAllEdges();
}
