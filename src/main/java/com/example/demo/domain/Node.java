package com.example.demo.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * @author cyk
 * 节点实体类,从neo4j中映射数据点。
 * 此处的 @NodeEntity后参数是映射到 neo4j数据库中节点的标签，默认为类名。
 */

@NodeEntity("Node")
public class Node {
    @Id
    @GeneratedValue
    private Long id;

    @Property("ID")
    private String ip;

    @Property("InD")
    private Long ind;

    @Property("OD")
    private Long od;

    @Property("IDW")
    private Long idW;

    @Property("ODW")
    private Long odw;

    @Property("Closeness")
    private double closeness;

    @Property("Betweenness")
    private double betweenness;

    @Property("EigenVector")
    private double eigenVector;

    @Property("Ave_Neighbor_Degree")
    private double aveNeighborDegree;

    @Property("LCC")
    private double lcc;

    @Property("Label")
    private String label;

    public Node() {
    }

    public Node(Long id, String ip, Long ind, Long od, Long idW, Long odw, double closeness, double betweenness, double eigenVector, double aveNeighborDegree, double lcc, String label) {
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
        return "Node{" +
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
