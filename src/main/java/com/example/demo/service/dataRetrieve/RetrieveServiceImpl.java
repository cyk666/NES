package com.example.demo.service.dataRetrieve;

import com.alibaba.fastjson.JSON;
import com.example.demo.elasticsearch.pojo.EsEdge;
import com.example.demo.neo4j.pojo.Edge;
import com.example.demo.neo4j.pojo.Node;
import com.example.demo.neo4j.repository.EdgeRepository;
import com.example.demo.neo4j.repository.NodeRepository;
import com.example.demo.utils.IpQuintuple;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.constant.Constant.EDGE_INDEX;

/**
 * @author cyk
 * 数据检索的方法在这里实现。
 */
@Service
public class RetrieveServiceImpl implements RetrieveService {

    @Autowired
    NodeRepository nodeRepository;

    @Autowired
    EdgeRepository edgeRepository;

    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * 实现测试方法，在Service层调用NodeRepository中定义的 findByIp
     *
     * @param ip ,需要查询的ip
     * @return 返回一个 neo4j 中的 Node 对象
     */
    @Override
    public Node findNodeByIp(String ip) {
        return nodeRepository.findByIp(ip);
    }

    /**
     * 检索 neo4j 中的所有的点
     * @return 返回一个 点Node 类组成的List
     */
    @Override
    public List<Node> findAllNodes() {
        return nodeRepository.findAllNodes();
    }

    /**
     * 根据一个 ip 返回带有该条边的所有的 边的List
     * @param ip 需要查询的ip
     * @return
     */
    @Override
    public List<Edge> findEdgeByIp(String ip) {
        return edgeRepository.findEdgeByIp(ip);
    }

    /**
     * 检索 neo4j 中的所有的边
     *
     * @return 返回一个 边Edge 类组成的 List
     */
    @Override
    public List<Edge> findAllEdges() {
        return edgeRepository.findAllEdges();
    }

    /**
     * 检索出一个五元组，
     * @param edgeList ,从 neo4j 中检索出需要的边组成 List<Edge></>, 作为参数输入
     * @return 返回一个 List<IpQuintuple>
     * @throws IOException
     */
    @Override
    public List<IpQuintuple> getIpQuintuple(List<Edge> edgeList) throws IOException {
        List<IpQuintuple> ipQuintupleList = new ArrayList<>();

        for (Edge edge : edgeList) {
            String startIp = edge.getStartNode().getIp();
            Long startId = edge.getStartNode().getId();
            String endIp = edge.getEndNode().getIp();
            Long endId = edge.getEndNode().getId();

            CountRequest countRequest = new CountRequest(EDGE_INDEX);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                    .filter(QueryBuilders.termQuery("SID", startIp))
                    .filter(QueryBuilders.termQuery("EID", endIp));
            countRequest.source(searchSourceBuilder.query(queryBuilder));

            CountResponse countResponse = restHighLevelClient.count(countRequest, RequestOptions.DEFAULT);
            long count = countResponse.getCount();

            IpQuintuple ipQuintuple = new IpQuintuple();
            ipQuintuple.setStartIp(startIp);
            ipQuintuple.setStartId(startId);
            ipQuintuple.setEndIp(endIp);
            ipQuintuple.setEndId(endId);
            ipQuintuple.setWeight(count);
            ipQuintupleList.add(ipQuintuple);
        }
        return ipQuintupleList;
    }

    /**
     * 在 neo4j 中两个ip之间只存储了一条边 edge, 实际上这一条边对应了 ES 中很多条带属性的边
     * 检索一条边所有的带属性的边
     * @param edge 需要检索的边
     * @param index 指定在ES检索的 index
     * @return 返回一个 List<EsEdge></>, 包含所有边的列表
     * @throws IOException
     */
    @Override
    public List<EsEdge> getFieldEdges(Edge edge, String index) throws IOException {
        List<EsEdge> esEdgeList = new ArrayList<>();

        String startIp = edge.getStartNode().getIp();
        String endIp = edge.getEndNode().getIp();

        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(10000);
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.filter(QueryBuilders.termQuery("SID", startIp))
                .filter(QueryBuilders.termQuery("EID", endIp));

        searchSourceBuilder.query(queryBuilder);
        searchRequest.source(searchSourceBuilder);
        searchRequest.scroll(TimeValue.timeValueMinutes(1L));

        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        assert response != null;
        String scrollId;

        do {
            for (SearchHit hit: response.getHits().getHits()) {
                EsEdge esEdge = JSON.parseObject(hit.getSourceAsString(), EsEdge.class);
                esEdgeList.add(esEdge);
            }
            scrollId = response.getScrollId();

            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(TimeValue.timeValueSeconds(30));
            response = restHighLevelClient
                    .scroll(scrollRequest, RequestOptions.DEFAULT);


        } while (response.getHits().getHits().length != 0);

        //清除滚屏
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);
        ClearScrollResponse clearScrollResponse = restHighLevelClient
                .clearScroll(clearScrollRequest, RequestOptions.DEFAULT);

        boolean succeeded = false;
        if (clearScrollResponse!=null){
            succeeded = clearScrollResponse.isSucceeded();
        }
        return esEdgeList;
    }

    /**
     * 检索一个边列表 List<Edge></> 中,每一条边的所有带属性的边
     * @param edgeList
     * @param index
     * @return List<EsEdges></>
     * @throws IOException
     */
    @Override
    public List<EsEdge> getFieldEdges(List<Edge> edgeList, String index) throws IOException {
        List<EsEdge> esEdgeList = new ArrayList<>();

        //遍历需要查找属性的边列表
        for (Edge edge: edgeList) {
            List<EsEdge> esEdges = getFieldEdges(edge, index);
            esEdgeList.addAll(esEdges);
        }
        return esEdgeList;
    }

}
