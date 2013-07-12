package org.atrion.astar;

import org.atrion.geometry.Point;
import org.atrion.graph.Graph;
import org.atrion.graph.Node;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 06/07/13
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */

public class AStar {

    private Path path;


    public  void compute(Graph graph){

    }

    public Path getPath(){
        return this.path;
    }

    private  void constructPath(Map<Node,Node> cameFrom, Path path, Node current){
         if(cameFrom.containsKey(current)){

             path.getNodes().add(current);

             Node next = cameFrom.get(current);
             constructPath(cameFrom,path, next);

         }
        else{
             path.getNodes().add(current);
         }
    }

    public void distance(Graph graph,Node start, Node destination){


        ArrayList<Node> closedSet = new ArrayList<Node>();
        PriorityQueue<Entity> openSet = new PriorityQueue<Entity>();
        Map<Node,Entity> entities = new HashMap<Node, Entity>();
        Map<Node,Node> cameFrom = new HashMap<Node, Node>();
        this.path = new Path(start,destination);

        Entity eStart = new Entity(start);
        eStart.gScore = 0f;
        eStart.fScore = eStart.gScore + heuristic(eStart.node,destination);

        openSet.add(eStart);
        entities.put(start,eStart);

        while(!openSet.isEmpty()){
//            System.out.println(openSet);
            Entity current = openSet.poll();
//            System.out.println("Current "+current.node.getId());
            if(current.node.equals(destination)){
                this.path.setTotalCost(current.gScore);
                constructPath(cameFrom,path,destination);
                return ;
            }

            closedSet.add(current.node);
            List<Node> currentNeighbors = graph.getNeighbors(current.node);

            if(currentNeighbors!=null){
                for(Node neighbor : currentNeighbors){
//                    System.out.println("\t\tNeighbor "+neighbor.getId());
                    float tentative_g_score =  current.gScore + graph.getEdge(current.node,neighbor).getCost();
                    Entity eNeighbor = new Entity(neighbor);

                    if(closedSet.contains(neighbor)){
                        float gScore = entities.get(neighbor).gScore;
                        if(tentative_g_score >= gScore){
                            // There is no better result
                            continue;
                        }

                    }

                    // Include in the queue
                    if(!openSet.contains(eNeighbor)){

                        cameFrom.put(neighbor,current.node);  // Where the neighbor node  came from (current node)

                        eNeighbor.gScore = tentative_g_score;
                        eNeighbor.fScore = eNeighbor.gScore + heuristic(eNeighbor.node,destination);

                        openSet.offer(eNeighbor);
                        entities.put(eNeighbor.node,eNeighbor);
                    }
                    // It is the queue, update its properties
                    else{
                        Entity e = entities.get(eNeighbor.node);
                        e.gScore = tentative_g_score;
                        e.fScore = eNeighbor.gScore + heuristic(e.node,destination);

                    }

                }
            }

        }

    }

    /**
     * Heuristic used in the Algorithm A*. Here we use Euclidean Distance
     * @param n1
     * @param n2
     * @return
     */
    private  float heuristic(Node n1, Node n2){
        Point l1 = n1.getPoint();
        Point l2 = n2.getPoint();
        float d = (float)Math.sqrt( Math.pow(l1.getX()-l2.getX(),2)+Math.pow(l1.getY()-l2.getY(),2));

        return  d;
    }

    private  class Entity implements Comparable<Entity>{

        private final Node node;
        private Node parent;
        private float gScore;
        private float fScore;

        public Entity(Node node){
            this.node = node;
        }

        @Override
        public int compareTo(Entity o) {
            return Float.compare(this.fScore,o.fScore);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Entity entity = (Entity) o;

            if (!node.equals(entity.node)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return node.hashCode();
        }

        @Override
        public String toString() {
            return "Entity{" +
                    "node=" + node.getId() +
                    ", fScore=" + fScore +
                    '}';
        }
    }

}
