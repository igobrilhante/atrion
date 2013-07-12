package org.atrion.astar;

import org.atrion.graph.Graph;
import org.atrion.graph.Node;

import java.io.*;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 11/07/13
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */
public class PathCollection implements Serializable {

    private HashMap<Node,HashMap<Node,Path>> map;
    private final boolean directed;

    public PathCollection(boolean directed){
        this.map = new HashMap<Node, HashMap<Node, Path>>();
        this.directed = directed;
    }

    public boolean isDirected() {
        return directed;
    }

    public HashMap<Node, HashMap<Node, Path>> getMap() {
        return map;
    }

    public void setMap(HashMap<Node, HashMap<Node, Path>> map) {
        this.map = map;
    }

    public Path getPath(Node source,Node target){
        if(directed){
            if(this.map.containsKey(source)){
                if(this.map.get(source).containsKey(target)){
                    return this.map.get(source).get(target);
                }
            }
        }
        else{
            Node n1 = null;
            Node n2 = null;

            if(source.getId() < target.getId()){
                n1 = source;
                n2 = target;

            }
            else{
                n1 = target;
                n2 = source;
            }

            if(this.map.containsKey(n1)){
                if(this.map.get(n1).containsKey(n2)){
                    return this.map.get(n1).get(n2);
                }
            }
        }

        return null;
    }

    public void addPath(Path path){

        if(directed){
            if(!map.containsKey(path.getStart())) {
                map.put(path.getStart(),new HashMap<Node, Path>());
            }
            map.get(path.getStart()).put(path.getDestination(),path);
        }
        else{
            Node n1 = null;
            Node n2 = null;

            if(path.getStart().getId() < path.getDestination().getId()){
                n1 = path.getStart();
                n2 = path.getDestination();

            }
            else{
                n1 = path.getDestination();
                n2 = path.getStart();
            }

            if(!map.containsKey(n1)){
                 map.put(n1,new HashMap<Node, Path>());
            }

            map.get(n1).put(n2,path);
        }
    }

    public static PathCollection read(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fileIn  = new FileInputStream(fileName);
        ObjectInputStream in    = new ObjectInputStream(fileIn);

        PathCollection pathCollection = (PathCollection) in.readObject();

        in.close();
        fileIn.close();
        return pathCollection;
    }

    public Double getCost(Node source, Node target){
        if(source.equals(target)){
            return 0.0;
        }

        Path path = this.getPath(source,target);

        if(path != null){
            return path.getTotalCost();
        }

        return null;

    }

    public static void write(String fileName,PathCollection pathCollection) throws IOException {
        File file = new File(fileName);

        FileOutputStream fileOut    =  new FileOutputStream(file);
        ObjectOutputStream out      =  new ObjectOutputStream(fileOut);

        out.writeObject(pathCollection);
        out.close();
        fileOut.close();
    }

}
