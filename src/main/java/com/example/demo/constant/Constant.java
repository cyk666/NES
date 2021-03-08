package com.example.demo.constant;

public final class Constant {
     private Constant() {
          super();
     }
     /**
      * Neo4j 中的常量定义
      */
     public static final String NODE = "IP";

     public static final String EDGE = "FLOW";

     /**
      * ElasticSearch 中的常量定义
      */
     public static final String NODE_INDEX= "node";

     public static final String EDGE_INDEX = "relationship";

     public static String getNodeIndex() {
          return NODE_INDEX;
     }

     public static String getEdgeIndex() {
          return EDGE_INDEX;
     }

     public static String getNODE() {
          return NODE;
     }

     public static String getEDGE() {
          return EDGE;
     }
}
