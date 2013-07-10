package org.atrion.graph.io;

import org.atrion.geometry.Point;
import org.atrion.graph.Edge;
import org.atrion.graph.Graph;
import org.atrion.graph.Node;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 06/07/13
 * Time: 17:07
 * To change this template use File | Settings | File Templates.
 */
public class GraphReader {

    public static Graph readFromAdjacentList(String adjacentFile) throws IOException {

        Graph graph = new Graph();

        BufferedReader br = new BufferedReader(new FileReader(new File(adjacentFile)));

        String line = "";
        while((line = br.readLine())!=null){
            String[] l = line.split(",");

            Integer source     =  Integer.parseInt(l[0]);

            Node nSource = new Node(source);

            for(int i=1;i<l.length;i++){

                String[] next      =  l[i].split(";");

                Integer target     =  Integer.parseInt(next[0]);
                Float   cost       =  Float.parseFloat(next[1]);

                Node nTarget  = new Node(target);

                Edge edge  =  new Edge(nSource,nTarget,cost);
                graph.addEdge(edge);
            }
        }
        return graph;
    }

    public static Graph readFromCSV(String fileName) throws IOException {

        Graph graph = new Graph();

        BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));

        String line = "";
        while((line = br.readLine())!=null){
            String[] l = line.split(",");

            Integer source     =  Integer.parseInt(l[0]);

            Node nSource = new Node(source);

            Integer target     =  Integer.parseInt(l[1]);
            Float   cost       =  Float.parseFloat(l[2]);

            Node nTarget  = new Node(target);

            Edge edge  =  new Edge(nSource,nTarget,cost);
            graph.addEdge(edge);
        }
        return graph;
    }

    public static Graph readFromCSV(String nodeFile,String edgeFile) throws IOException {

        Graph graph = new Graph();

        BufferedReader br = new BufferedReader(new FileReader(new File(nodeFile)));

        String line = "";
        while((line = br.readLine())!=null){
            String[] l = line.split(",");

            Integer nodeId  = Integer.parseInt(l[0]);
            Double x = Double.parseDouble(l[1]);
            Double y= Double.parseDouble(l[2]);

            Node node = new Node(nodeId);
            Point point = new Point(x,y);

            node.setPoint(point);
            graph.addNode(node);
        }
        br.close();

        br = new BufferedReader(new FileReader(new File(edgeFile)));

        line = "";
        while((line = br.readLine())!=null){
            String[] l = line.split(",");

            Integer source     =  Integer.parseInt(l[0]);

            Node nSource = new Node(source);

            Integer target     =  Integer.parseInt(l[1]);
            Float   cost       =  Float.parseFloat(l[2]);

            Node nTarget  = new Node(target);

            Edge edge  =  new Edge(nSource,nTarget,cost);
            graph.addEdge(edge);
        }

        return graph;
    }

    public static Graph readObject(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fileIn  = new FileInputStream(fileName);
        ObjectInputStream in    = new ObjectInputStream(fileIn);

        Graph graph = (Graph) in.readObject();

        in.close();
        fileIn.close();

        return graph;
    }

}
