package com.example.demo.elasticsearch.repository;

import com.example.demo.domain.Node;
import com.example.demo.elasticsearch.esPojo.esNode;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author cyk
 * Elasticsearch提供的数据持久化，对于es中节点的检索，删除使用该接口下定义函数。
 */
public interface esNodeRepository extends ElasticsearchRepository<esNode, Long> {

    //测试用的方法，根据输入的属性ip来从neo4j中检索节点，返回结果正确则neo4j配置无误。
    esNode findByIp(@Param("ip") String ip);
}
