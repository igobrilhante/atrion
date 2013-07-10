//package org.atrion.test;
//
//
//import com.vividsolutions.jts.geom.Coordinate;
//import com.vividsolutions.jts.geom.GeometryFactory;
//import com.vividsolutions.jts.geom.LineString;
//import com.vividsolutions.jts.geom.Point;
//import org.geotools.data.DataUtilities;
//import org.geotools.data.simple.SimpleFeatureCollection;
//import org.geotools.feature.DefaultFeatureCollection;
//import org.geotools.feature.FeatureIterator;
//import org.geotools.feature.SchemaException;
//import org.geotools.feature.simple.SimpleFeatureBuilder;
//import org.geotools.feature.simple.SimpleFeatureImpl;
//import org.geotools.geometry.jts.JTSFactoryFinder;
//import org.geotools.graph.build.feature.FeatureGraphGenerator;
//import org.geotools.graph.build.line.DirectedLineStringGraphGenerator;
//import org.geotools.graph.path.DijkstraShortestPathFinder;
//import org.geotools.graph.path.Path;
//import org.geotools.graph.structure.*;
//import org.geotools.graph.traverse.standard.DijkstraIterator;
//import org.geotools.graph.traverse.standard.DirectedDijkstraIterator;
//import org.opengis.feature.Feature;
//import org.opengis.feature.simple.SimpleFeature;
//import org.opengis.feature.simple.SimpleFeatureType;
//import org.opengis.geometry.Geometry;
//
//import java.io.*;
//import java.util.*;
//
///**
//* Created with IntelliJ IDEA.
//* User: igobrilhante
//* Date: 03/07/13
//* Time: 00:50
//* To change this template use File | Settings | File Templates.
//*/
//public class GraphGeoToolTest {
//
//    public static DefaultFeatureCollection load() throws SchemaException {
//
//        File nodeFile = new File("road-network-nodes.txt");
//        File edgeFile = new File("road-network-edges.txt");
//
//        ArrayList<LineString> list = new ArrayList<LineString>();
//
//        /*
//         * We use the DataUtilities class to create a FeatureType that will describe the data in our
//         * shapefile.
//         *
//         * See also the createFeatureType method below for another, more flexible approach.
//         */
//        final SimpleFeatureType NODE_TYPE = DataUtilities.createType("Node",
//                "org.atrion.location:Point," +
//                "id:Integer"
//        );
//        final SimpleFeatureType EDGE_TYPE = DataUtilities.createType("Edge",
//                "org.atrion.location:LineString," +
//                "label:String," +
//                "source:Integer," +
//                "target:Integer"
//        );
//
//        /*
//         * We create a FeatureCollection into which we will put each Feature created from a record
//         * in the input csv data file
//         */
//        DefaultFeatureCollection collection = new DefaultFeatureCollection("internal");
//        /*
//         * GeometryFactory will be used to create the geometry attribute of each feature (a Point
//         * object for the org.atrion.location)
//         */
//        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
//
//        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(EDGE_TYPE);
//
//        int count = 0;
//
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(nodeFile));
//            String line = "";
//            HashMap<Integer,Coordinate> map = new HashMap<Integer, Coordinate>();
//            while((line = br.readLine())!=null){
//                String[] node = line.split(",");
//
//                Integer id = Integer.parseInt(node[0]);
//
//                Double latitude  = Double.parseDouble(node[1]);
//                Double longitude = Double.parseDouble(node[2]);
//
//                Coordinate coordinate = new Coordinate(longitude,latitude);
//
////                Point point = geometryFactory.createPoint(coordinate);
//
//                map.put(id,coordinate);
//
////                featureBuilder.add(point);
////                featureBuilder.add(id);
//
////                SimpleFeature simpleFeature = featureBuilder.buildFeature(id.toString());
////                collection.add(simpleFeature);
//
//
//            }
//            br.close();
//            br = new BufferedReader(new FileReader(edgeFile));
//            line = "";
//            Integer edgeId = 0;
//            while((line = br.readLine())!=null){
//                String[] node = line.split(",");
//
//                Integer source = Integer.parseInt(node[0]);
//
//                Integer target = Integer.parseInt(node[1]);
//
//                String directed = node[2];
//
//                Coordinate sourcePoint = map.get(source);
//                Coordinate targetPoint = map.get(target);
//
//                Coordinate[] coords = {sourcePoint,targetPoint};
//
//                LineString lineString = geometryFactory.createLineString(coords);
//
//                featureBuilder.add(lineString);
//                featureBuilder.add(source+"-"+target);
//                featureBuilder.add(source);
//                featureBuilder.add(target);
//
//                SimpleFeature simpleFeature = featureBuilder.buildFeature(edgeId.toString());
//                collection.add(simpleFeature);
//                edgeId++;
//
//                if(directed.equalsIgnoreCase("f")){
//
//                    featureBuilder.add(lineString);
//                    featureBuilder.add(target+"-"+source);
//                    featureBuilder.add(target);
//                    featureBuilder.add(source);
//                    SimpleFeature simpleFeature2 = featureBuilder.buildFeature(edgeId.toString());
//                    collection.add(simpleFeature2);
//
//                    edgeId++;
//                }
//
//
//            }
//            br.close();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//
//
//        return collection;
//    }
//
//    public static void main(String[] args) throws SchemaException {
//
//        DefaultFeatureCollection collection = load();
//
//        //create a linear graph generate
//        DirectedLineStringGraphGenerator lineStringGen = new DirectedLineStringGraphGenerator();
//
////wrap it in a feature graph generator
//        FeatureGraphGenerator featureGen = new FeatureGraphGenerator( lineStringGen );
//
////throw all the features into the graph generator
//        FeatureIterator iter = collection.features();
//        try {
//            while(iter.hasNext()){
//                Feature feature = iter.next();
//                featureGen.add( feature );
//            }
//        } finally {
//            iter.close();
//        }
//        DirectedGraph graph = (DirectedGraph)featureGen.getGraph();
//
//        System.out.println(graph.getNodes().size());
//        System.out.println(graph.getEdges().size());
//
//        for(Object i : graph.getNodes()){
//            Node node = (Node)i;
//            System.out.println(node+" "+node.getObject());
//        }
//
//        for(Object i : graph.getEdges()){
//            Edge edge = (Edge) i;
//            System.out.println(edge+" "+edge.getObject());
//        }
//
//        List<Node> nodes = new ArrayList<Node>(graph.getNodes());
//
//        //find a source node (usually your user chooses one)
//        Node start = nodes.get(0);
//
//// create a strategy for weighting edges in the graph
//// in this case we are using geometry length
//        DirectedDijkstraIterator.EdgeWeighter weighter = new DijkstraIterator.EdgeWeighter() {
//            public double getWeight(Edge e) {
//                SimpleFeature feature = (SimpleFeature) e.getObject();
//                LineString geometry = (LineString) feature.getDefaultGeometry();
//                return geometry.getLength();
//            }
//        };
//
//// Create GraphWalker - in this case DijkstraShortestPathFinder
//        DijkstraShortestPathFinder pf = new DijkstraShortestPathFinder( graph, start, weighter );
//        pf.calculate();
//
////find some destinations to calculate paths to
//        List/*<Node>*/ destinations = nodes;
//
////calculate the paths
//        for ( Iterator d = destinations.iterator(); d.hasNext(); ) {
//            Node destination = (Node) d.next();
//
//            Path path = pf.getPath( destination );
//
//            if(path.isValid()){
//                System.out.println(destination + " "+path.getEdges());
//            }
//
//        }
//    }
//}
