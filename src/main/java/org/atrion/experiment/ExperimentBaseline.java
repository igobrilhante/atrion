package org.atrion.experiment;

import org.atrion.algorithm.Atrion;
import org.atrion.algorithm.AtrionQuery;
import org.atrion.algorithm.AtrionRecommendation;
import org.atrion.entity.MovingObject;
import org.atrion.experiment.generator.PointGenerator;
import org.atrion.geometry.Point;
import org.atrion.graph.Graph;
import org.atrion.graph.io.GraphReader;
import org.atrion.index.SpatialIndex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 26/07/13
 * Time: 19:41
 * To change this template use File | Settings | File Templates.
 */
public class ExperimentBaseline {
    public static void main(String[] args) throws Exception {
        Graph walkingGraph = GraphReader.readObject("output/fortaleza-walking-network.ser");
        Graph roadGraph = GraphReader.readObject("output/fortaleza-road-network.ser");

        int[] points = {50,100,200};
        int[] mos = {100};

        SpatialIndex si = new SpatialIndex();
        si.init(walkingGraph);

        Atrion atrion = new Atrion();
        atrion.init(si);

        float walkingDistance = 0f;

        for(int k=0;k<3;k++){
            System.out.println("K "+k);
            for(int i=0;i<points.length;i++){
                System.out.println("I "+i);
                Collection<MovingObject> users = PointGenerator.readMOS("output/points-" + points[i] + "-" + k);
                for(int j=0; j<mos.length;j++){
                    System.out.println("J "+j);
                    Collection<MovingObject> m = PointGenerator.readMOS("output/mos-"+mos[j]+"-"+k);
                    File file = new File("baseline-"+walkingDistance+"-p"+points[i]+"mos-"+mos[j]+"-"+k);
                    BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                    for(MovingObject user : users){
                        AtrionQuery queryPoint = new AtrionQuery();
                        queryPoint.setPoint(user.getPoint());
                        queryPoint.setWalkingDistance(walkingDistance);
                        queryPoint.setDestinationPoint(new Point(-38.479235, -3.801867));

                        AtrionRecommendation ar = atrion.execute(queryPoint,m,roadGraph,walkingGraph,1);

                        if(ar.getList().size() > 0){
                            bw.write(user.getId()+","+ar.getList().get(0).getTotalCost()+"\n");
                            bw.flush();
                        }

                    }

                    bw.close();

                }
            }
        }

    }
}
