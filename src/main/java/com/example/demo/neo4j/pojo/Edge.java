package com.example.demo.neo4j.pojo;

import org.neo4j.ogm.annotation.*;

import static com.example.demo.constant.Constant.EDGE;


/**
 * @author cyk
 * 边实体类,从neo4j中映射边数据。
 * 此处的 @NodeEntity后参数是映射到 neo4j数据库中边的标签，默认为类名。
 * 如果有两种标签的边，则再创建一个边的实体类。
 */

@RelationshipEntity(type = EDGE)
public class Edge {

    //在neo4j中自动为其分配一个数字Id
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    Node startNode;

    @EndNode
    Node endNode;

    @Property("weight")
    Long weight;

    public Edge() {
    }

    public Long getId() {
        return id;
    }

    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "id=" + id +
                ", startNode=" + startNode +
                ", endNode=" + endNode +
                '}';
    }
}