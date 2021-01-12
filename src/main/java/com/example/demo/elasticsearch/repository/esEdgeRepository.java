package com.example.demo.elasticsearch.repository;

import com.example.demo.elasticsearch.esPojo.esNode;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author cyk
 * Elasticsearch提供的数据持久化，对于es中节点的检索，删除使用该接口下定义函数。
 */
public interface esEdgeRepository extends ElasticsearchRepository<esNode, Long> {

    //从ES从按照节点的Ip属性检索节点。
    esNode findByIp(String title);
}
