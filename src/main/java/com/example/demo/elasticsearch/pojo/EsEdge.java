package com.example.demo.elasticsearch.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import static com.example.demo.constant.Constant.EDGE_INDEX;

/**
 * @author cyk
 * 映射到es中索引下的边的信息，indexName 为索引名。
 * shards和replicas分别为分片和副本数量，根据实际ES中的值设置。
 */
@Document(indexName = EDGE_INDEX, shards = 1, replicas = 1)
public class EsEdge {
    //可手动设置数字Id值，否则ES将自动分配一个。
    @Id
    @JSONField(name = "_id")
    @Field (name = "_id")
    private String id;

    @Field(name = "_score")
    private Long score;

    @JSONField(name = "SID")
    @Field(name = "SID")
    private String startIp;

    @JSONField(name = "EID")
    @Field(name = "EID")
    private String endIp;

    @JSONField(name = "Protocol")
    @Field(name = "Protocol")
    private int protocol;

    @JSONField(name = "Flow Duration")
    @Field(name = "Flow Duration")
    private Long flowDuration;

    @JSONField(name = "Tot Fwd Pkts")
    @Field(name = "Tot Fwd Pkts")
    private Long totFwdPkts;

    @JSONField(name = "Tot Bwd Pkts")
    @Field(name = "Tot Bwd Pkts")
    private Long totBwdPkts;

    @JSONField(name = "TotLen Fwd Pkts")
    @Field(name = "TotLen Fwd Pkts")
    private double totLenFwdPkts;

    @JSONField(name = "TotLen Bwd Pkts")
    @Field(name = "TotLen Bwd Pkts")
    private double totLenBwdPkts;

    @JSONField(name = "DownUp Ratio")
    @Field(name = "DownUp Ratio")
    private double downUpRatio;

    @JSONField(name = "Pkt Size Avg")
    @Field(name = "Pkt Size Avg")
    private double pktSizeAvg;

    @JSONField(name = "TYPE")
    @Field(name = "TYPE")
    private String type;

    public EsEdge() {
    }

    public EsEdge(String id, String startIp, String endIp, int protocol, Long flowDuration, Long totFwdPkts, Long totBwdPkts, double totLenFwdPkts, double totLenBwdPkts, double downUpRatio, double pktSizeAvg, String type) {
        this.id = id;
        this.startIp = startIp;
        this.endIp = endIp;
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

    public String getId() {
        return id;
    }


    public String getStartIp() {
        return startIp;
    }

    public void setStartIp(String startIp) {
        this.startIp = startIp;
    }

    public String getEndIp() {
        return endIp;
    }

    public void setEndIp(String endIp) {
        this.endIp = endIp;
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
                ", startId='" + startIp + '\'' +
                ", endId='" + endIp + '\'' +
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
