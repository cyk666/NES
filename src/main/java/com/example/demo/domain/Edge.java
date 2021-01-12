package com.example.demo.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * @author cyk
 * 边实体类,从neo4j中映射边数据。
 * 此处的 @NodeEntity后参数是映射到 neo4j数据库中边的标签，默认为类名。
 * 如果有两个中标签的边，则再创建一个边的实体类。
 */

@NodeEntity("Edge")
public class Edge {

    //在neo4j中自动为其分配一个数字Id
    @Id
    @GeneratedValue
    private Long id;

    @Property("START_ID")
    private String startId;

    @Property("END_ID")
    private String endId;

    @Property("Protocol")
    private int protocol;

    @Property("Flow_Duration")
    private Long flowDuration;

    @Property("Tot_Fwd_Pkts")
    private Long totFwdPkts;

    @Property("Tot_Bwd_Pkts")
    private Long totBwdPkts;

    @Property("TotLen_Fwd_Pkts")
    private double totLenFwdPkts;

    @Property("TotLen_Bwd_Pkts")
    private double totLenBwdPkts;

    @Property("Down_Up_Ratio")
    private double downUpRatio;

    @Property("Pkt_Size_Avg")
    private double pktSizeAvg;

    @Property(":TYPE")
    private String type;

    public Edge() {
    }

    public Edge(Long id, String startId, String endId, int protocol, Long flowDuration, Long totFwdPkts, Long totBwdPkts, double totLenFwdPkts, double totLenBwdPkts, double downUpRatio, double pktSizeAvg, String type) {
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
