package com.example.demo.neo4j.pojo;

import com.example.demo.constant.Constant;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import static com.example.demo.constant.Constant.NODE;

/**
 * @author cyk
 * 节点实体类,从neo4j中映射数据点。
 * 此处的 @NodeEntity后参数是映射到 neo4j数据库中节点的标签，默认为类名。
 */

@NodeEntity(NODE)
public class Node {
    @Id
    @GeneratedValue
    private Long id;

    @Property("id")
    private String ip;


    public Node() {
    }

    public Node(Long id, String ip) {
        this.id = id;
        this.ip = ip;
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

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                '}';
    }
}