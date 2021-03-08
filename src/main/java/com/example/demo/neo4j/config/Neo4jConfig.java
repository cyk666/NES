package com.example.demo.neo4j.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;


/**
 * @author cyk
 * 配置类，包括注入Bean的文件夹位置和连接数据库的用户密码。
 */
@Configuration
@EnableNeo4jRepositories(basePackages = "com.example.demo.neo4j.repository")
public class Neo4jConfig {

}
