package com.example.demo.elasticsearch.pojo;

import org.neo4j.ogm.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import static com.example.demo.constant.Constant.NODE_INDEX;

/**
 * @author cyk
 * 将 节点 、 边的属性在ES中存入不同的索引。
 * 映射到es中索引下的节点，indexName 为索引名。
 * shards和replicas分别为分片和副本数量，根据实际ES中的值设置。
 */
@Document(indexName = NODE_INDEX, shards = 1, replicas = 1)
public class EsNode {
    @Id
    private Long id;

    @Field(name = "ID")
    private String ip;

    @Field(name ="InD")
    private Long ind;

    @Field(name ="OD")
    private Long od;

    @Field(name ="IDW")
    private Long idW;

    @Field(name ="ODW")
    private Long odw;

    @Field(name ="Closeness")
    private double closeness;

    @Field(name ="Betweenness")
    private double betweenness;

    @Field(name ="EigenVector")
    private double eigenVector;

    @Field(name ="Ave_Neighbor_Degree")
    private double aveNeighborDegree;

    @Field(name ="LCC")
    private double lcc;

    @Field(name ="Label")
    private String label;

    public EsNode() {
    }

    public EsNode(Long id, String ip, Long ind, Long od, Long idW, Long odw, double closeness, double betweenness, double eigenVector, double aveNeighborDegree, double lcc, String label) {
        this.id = id;
        this.ip = ip;
        this.ind = ind;
        this.od = od;
        this.idW = idW;
        this.odw = odw;
        this.closeness = closeness;
        this.betweenness = betweenness;
        this.eigenVector = eigenVector;
        this.aveNeighborDegree = aveNeighborDegree;
        this.lcc = lcc;
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getInd() {
        return ind;
    }

    public void setInd(Long ind) {
        this.ind = ind;
    }

    public Long getOd() {
        return od;
    }

    public void setOd(Long od) {
        this.od = od;
    }

    public Long getIdW() {
        return idW;
    }

    public void setIdW(Long idW) {
        this.idW = idW;
    }

    public Long getOdw() {
        return odw;
    }

    public void setOdw(Long odw) {
        this.odw = odw;
    }

    public double getCloseness() {
        return closeness;
    }

    public void setCloseness(double closeness) {
        this.closeness = closeness;
    }

    public double getBetweenness() {
        return betweenness;
    }

    public void setBetweenness(double betweenness) {
        this.betweenness = betweenness;
    }

    public double getEigenVector() {
        return eigenVector;
    }

    public void setEigenVector(double eigenVector) {
        this.eigenVector = eigenVector;
    }

    public double getAveNeighborDegree() {
        return aveNeighborDegree;
    }

    public void setAveNeighborDegree(double aveNeighborDegree) {
        this.aveNeighborDegree = aveNeighborDegree;
    }

    public double getLcc() {
        return lcc;
    }

    public void setLcc(double lcc) {
        this.lcc = lcc;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "EsNode{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", ind=" + ind +
                ", od=" + od +
                ", idW=" + idW +
                ", odw=" + odw +
                ", closeness=" + closeness +
                ", betweenness=" + betweenness +
                ", eigenVector=" + eigenVector +
                ", aveNeighborDegree=" + aveNeighborDegree +
                ", lcc=" + lcc +
                ", label='" + label + '\'' +
                '}';
    }
}
