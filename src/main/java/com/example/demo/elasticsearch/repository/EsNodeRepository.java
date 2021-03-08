package com.example.demo.elasticsearch.repository;

import com.example.demo.elasticsearch.pojo.EsNode;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author cyk
 * Elasticsearch提供的数据持久化，对于es中节点的检索使用该接口下定义函数。
 */
public interface EsNodeRepository extends ElasticsearchRepository<EsNode, Long> {

}
