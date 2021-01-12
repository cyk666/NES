package com.example.demo.elasticsearch.esPojo;

import org.neo4j.ogm.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * @author cyk
 * 映射到es中索引下的边的信息，indexName 为索引名。
 * shards和replicas分别为分片和副本数量，根据实际ES中的值设置。
 */
@Document(indexName = "esEdge", shards = 1, replicas = 1)
public class esEdge {

    //可手动设置数字Id值，否则ES将自动分配一个。
    @Id
    private Long id;

    @Field(name = "START_ID")
    private String startId;

    @Field(name = "END_ID")
    private String endId;

    @Field(name = "Protocol")
    private int protocol;

    @Field(name = "Flow_Duration")
    private Long flowDuration;

    @Field(name = "Tot_Fwd_Pkts")
    private Long totFwdPkts;

    @Field(name = "Tot_Bwd_Pkts")
    private Long totBwdPkts;

    @Field(name = "TotLen_Fwd_Pkts")
    private double totLenFwdPkts;

    @Field(name = "TotLen_Bwd_Pkts")
    private double totLenBwdPkts;

    @Field(name = "Down_Up_Ratio")
    private double downUpRatio;

    @Field(name = "Pkt_Size_Avg")
    private double pktSizeAvg;

    @Field(name = ":TYPE")
    private String type;

    public esEdge() {
    }

    public esEdge(Long id, String startId, String endId, int protocol, Long flowDuration, Long totFwdPkts, Long totBwdPkts, double totLenFwdPkts, double totLenBwdPkts, double downUpRatio, double pktSizeAvg, String type) {
        this.id = id;
        this.startId = startId;
        this.endId = endId;
        this.protocol = protocol;
        this.flowDuration = flowDuration;
        this.totFwdPkts = totFwdPkts;
        this.totBwdPkts = totBwdPkts;
        this.totLenFwdPkts = totLenFwdPkts;
        this.totLenBwdPkts = totLenBwdPkts;
        this.downUpRatio = downUpRatio;
        this.pktSizeAvg = pktSizeAvg;
        this.type = type;
    }

    public Long getId() {
        return id;
    }


    public String getStartId() {
        return startId;
    }

    public void setStartId(String startId) {
        this.startId = startId;
    }

    public String getEndId() {
        return endId;
    }

    public void setEndId(String endId) {
        this.endId = endId;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public Long getFlowDuration() {
        return flowDuration;
    }

    public void setFlowDuration(Long flowDuration) {
        this.flowDuration = flowDuration;
    }

    public Long getTotFwdPkts() {
        return totFwdPkts;
    }

    public void setTotFwdPkts(Long totFwdPkts) {
        this.totFwdPkts = totFwdPkts;
    }

    public Long getTotBwdPkts() {
        return totBwdPkts;
    }

    public void setTotBwdPkts(Long totBwdPkts) {
        this.totBwdPkts = totBwdPkts;
    }

    public double getTotLenFwdPkts() {
        return totLenFwdPkts;
    }

    public void setTotLenFwdPkts(double totLenFwdPkts) {
        this.totLenFwdPkts = totLenFwdPkts;
    }

    public double getTotLenBwdPkts() {
        return totLenBwdPkts;
    }

    public void setTotLenBwdPkts(double totLenBwdPkts) {
        this.totLenBwdPkts = totLenBwdPkts;
    }

    public double getDownUpRatio() {
        return downUpRatio;
    }

    public void setDownUpRatio(double downUpRatio) {
        this.downUpRatio = downUpRatio;
    }

    public double getPktSizeAvg() {
        return pktSizeAvg;
    }

    public void setPktSizeAvg(double pktSizeAvg) {
        this.pktSizeAvg = pktSizeAvg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "id=" + id +
                ", startId='" + startId + '\'' +
                ", endId='" + endId + '\'' +
                ", protocol=" + protocol +
                ", flowDuration=" + flowDuration +
                ", totFwdPkts=" + totFwdPkts +
                ", totBwdPkts=" + totBwdPkts +
                ", totLenFwdPkts=" + totLenFwdPkts +
                ", totLenBwdPkts=" + totLenBwdPkts +
                ", downUpRatio=" + downUpRatio +
                ", pktSizeAvg=" + pktSizeAvg +
                ", type='" + type + '\'' +
                '}';
    }
}
