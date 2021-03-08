package com.example.demo.elasticsearch.repository;

import com.example.demo.elasticsearch.pojo.EsEdge;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author cyk
 * Elasticsearch提供的数据持久化，对于es中节点的检索使用该接口下定义函数。
 */
public interface EsEdgeRepository extends ElasticsearchRepository<EsEdge, Long> {

}
