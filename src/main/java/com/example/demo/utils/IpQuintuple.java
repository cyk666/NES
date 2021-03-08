package com.example.demo.utils;

public class IpQuintuple {
    private String startIp;
    private Long startId;
    private String endIp;
    private Long endId;
    private Long weight = 0L;

    public IpQuintuple() {
    }

    public IpQuintuple(String startIp, Long startId, String endIp, Long endId, Long weight) {
        this.startIp = startIp;
        this.startId = startId;
        this.endIp = endIp;
        this.endId = endId;
        this.weight = weight;
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

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getStartId() {
        return startId;
    }

    public void setStartId(Long startId) {
        this.startId = startId;
    }

    public Long getEndId() {
        return endId;
    }

    public void setEndId(Long endId) {
        this.endId = endId;
    }

    @Override
    public String toString() {
        return "IpQuintuple{" +
                "startIp='" + startIp + '\'' +
                ", startId=" + startId +
                ", endIp='" + endIp + '\'' +
                ", endId=" + endId +
                ", wight=" + weight +
                '}';
    }


}












