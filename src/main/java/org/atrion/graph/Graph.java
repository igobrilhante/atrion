package org.atrion.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 06/07/13
 * Time: 12:34
 * To change this template use File | Settings | File Templates.
 */
public class Graph implements Serializable {

    private HashMap<Integer,Node> nodes;
    private List<Edge> edges;
    private HashMap<Node,List<Node>> adjacentList;
    private HashMap<String,Edge> edgeMap;

    public Graph(){
        nodes = new HashMap<Integer,Node>();
        edges = new ArrayList<Edge>();
        adjacentList = new HashMap<Node, List<Node>>();
        edgeMap = new HashMap<String, Edge>();
    }

    public Node getNode(Integer i){
        return nodes.get(i);
    }

    public int nodeCount(){
        return nodes.size();
    }

    public int edgeCount(){
        return edges.size();
    }

    public Edge getEdge(int i){
        return edges.get(i);
    }

    public List<Node> getNeighbors(Node node){
         return adjacentList.get(node);
    }

    public void addNode(Node node){

        if(nodes.containsKey(node.getId())){
            return;
        }

        this.nodes.put(node.getId(),node);
    }

    public Collection<Node> getNodes(){
        return this.nodes.values();
    }

    public void addEdge(Edge edge){
        if(edges.contains(edge)){
            return;
        }
        addNode(edge.getSource());
        addNode(edge.getTarget());

        Node source = this.nodes.get(edge.getSource().getId());
        Node target = this.nodes.get(edge.getTarget().getId());


        if(!adjacentList.containsKey(source)){
           adjacentList.put(source,new ArrayList<Node>());
        }
        adjacentList.get(source).add(target);

        this.edgeMap.put(source.getId()+"-"+target.getId(),edge);
        this.edges.add(new Edge(source,target,edge.getCost()));
    }

    public Collection<Edge> getEdges(){
        return edges;
    }

    public Edge getEdge(Node source,Node target){

        return this.edgeMap.get(source.getId()+"-"+target.getId());
    }



}
