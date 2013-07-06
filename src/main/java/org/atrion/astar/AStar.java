package org.atrion.astar;

import org.atrion.graph.Graph;
import org.atrion.graph.Node;
import org.atrion.location.Location;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 06/07/13
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */


//function A*(start,goal)
//        closedset := the empty set    // The set of nodes already evaluated.
//        openset := {start}    // The set of tentative nodes to be evaluated, initially containing the start node
//        came_from := the empty map    // The map of navigated nodes.
//
//        g_score[start] := 0    // Cost from start along best known path.
//        // Estimated total cost from start to goal through y.
//        f_score[start] := g_score[start] + heuristic_cost_estimate(start, goal)
//
//        while openset is not empty
//        current := the node in openset having the lowest f_score[] value
//        if current = goal
//        return reconstruct_path(came_from, goal)
//
//        remove current from openset
//        add current to closedset
//        for each neighbor in neighbor_nodes(current)
//        tentative_g_score := g_score[current] + dist_between(current,neighbor)
//        if neighbor in closedset and tentative_g_score >= g_score[neighbor]
//        continue
//
//        if neighbor not in openset or tentative_g_score < g_score[neighbor]
//        came_from[neighbor] := current
//        g_score[neighbor] := tentative_g_score
//        f_score[neighbor] := g_score[neighbor] + heuristic_cost_estimate(neighbor, goal)
//        if neighbor not in openset
//        add neighbor to openset
//
//        return failure
//
//        function reconstruct_path(came_from, current_node)
//        if current_node in came_from
//        p := reconstruct_path(came_from, came_from[current_node])
//        return (p + current_node)
//        else
//        return current_node
public class AStar {



    public static void compute(Graph graph){

    }

    public static void astart(Graph graph,Node start, Node destination){


        ArrayList<Node> closedSet = new ArrayList<Node>();
        PriorityQueue<Entity> openSet = new PriorityQueue<Entity>();
        Map<Node,Entity> entities = new HashMap<Node, Entity>();

        Entity eStart = new Entity(start);
        eStart.gScore = 0f;
        eStart.fScore = eStart.gScore + heuristic(eStart.node,destination);

        openSet.add(eStart);
        entities.put(start,eStart);

        while(!openSet.isEmpty()){
            System.out.println(openSet);
            Entity current = openSet.poll();
            System.out.println("Current "+current.node.getId());
            if(current.node.equals(destination)){
                System.out.println("Total cost "+current.gScore);
                return ;
            }

            closedSet.add(current.node);
            List<Node> currentNeighbors = graph.getNeighbors(current.node);

            if(currentNeighbors!=null){
                for(Node neighbor : currentNeighbors){
                    System.out.println("\t\tNeighbor "+neighbor.getId());
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
                        // cameFrom
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

    private static float heuristic(Node n1, Node n2){
        Location l1 = n1.getLocation();
        Location l2 = n2.getLocation();
        float d = (float)Math.sqrt( Math.pow(l1.getLongitude()-l2.getLongitude(),2)+Math.pow(l1.getLatitude()-l2.getLatitude(),2));

        return  d;
    }

    private static class Entity implements Comparable<Entity>{

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
