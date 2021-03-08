package com.example.demo.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


/**
 * @author cyk
 * 配置类。
 * 该 EsConfig 是配置连接ES有关的信息，包括注入 Bean 时的文件夹位置，使用RestHighLevelClient的方法。
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.demo.elasticsearch.repository")
public class EsConfig {
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
        return client;
    }
}
