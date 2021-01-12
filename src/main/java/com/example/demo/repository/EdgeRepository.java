package com.example.demo.repository;

import com.example.demo.domain.Edge;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author cyk
 * 继承spring-data-neo4j提供的接口。对于neo4j中边的简单CRUD方法定义在这里。
 */
public interface EdgeRepository extends Neo4jRepository<Edge, Long> {
}
