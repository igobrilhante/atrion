package org.atrion.experiment.generator;

import org.atrion.distance.EuclideanDistance;
import org.atrion.distance.Util;
import org.atrion.entity.MovingObject;
import org.atrion.geometry.Point;
import org.atrion.graph.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 26/07/13
 * Time: 15:01
 * To change this template use File | Settings | File Templates.
 */
public class PointGenerator {

    public static Collection<Point> generate(Point point, double minRadius,double maxRadius, int numberOfPoints){

        Random random = new Random();
        Collection<Point> points = new ArrayList<Point>();
        double r = 0.0;

        for(int i=0;i<numberOfPoints;i++){

            double newX = minRadius + point.getX();
            r = random.nextGaussian();
            newX = newX + (maxRadius-minRadius)*r;

            double newY = minRadius + point.getY();
            r = random.nextGaussian();
            newY = newY + (maxRadius-minRadius)*r;



            Point newPoint = new Point(newX,newY);
            points.add(newPoint);
        }

        return points;

    }

    public static Collection<MovingObject> generateMOs(Point point, double minRadius,double maxRadius, int numberOfMO){
        Collection<MovingObject> mos = new ArrayList<MovingObject>();
        Collection<Point> points = generate(point, minRadius, maxRadius, numberOfMO);

        int i = 0;
        for(Point p : points){
            MovingObject mo = new MovingObject();
            mo.setId(i);
            mo.setPoint(p);

            mos.add(mo);
            i++;
        }

        return mos;

    }

    public static Collection<Point> read(String filename){
        Collection<Point> res = new ArrayList<Point>();
        File file = new File(filename);

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            while((line = br.readLine())!=null){
                String[] l = line.split(",");
                Point p = new Point(Double.parseDouble(l[1]),Double.parseDouble(l[2]));
                res.add(p);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try{br.close();}catch (Exception e){}
        }

        return res;

    }

    public static Collection<MovingObject> readMOS(String filename){
        Collection<MovingObject> res = new ArrayList<MovingObject>();
        File file = new File(filename);

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            while((line = br.readLine())!=null){
                String[] l = line.split(",");
                int id = Integer.parseInt(l[0]);
                Point p = new Point(Double.parseDouble(l[1]),Double.parseDouble(l[2]));
                MovingObject mo = new MovingObject();
                mo.setPoint(p);
                mo.setId(id);
                res.add(mo);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try{br.close();}catch (Exception e){}
        }

        return res;

    }

    public static void main(String[] args){
        Point p = new Point(-38.539194,-3.734336);
        int k =3;
        int[] total = {50,100,200};

        for(int j : total){

            for(int i=0;i<k;i++){
                Collection<Point> ps = generate(p, Util.meters2degree(1000),Util.meters2degree(5000),j);

                File file = new File("output/points-"+j+"-"+i);
                BufferedWriter bout = null;
                try {
                    bout = new BufferedWriter(new FileWriter(file));
                    int id = 0;
                    for(Point p1 : ps){
                        bout.write(String.format("%s,%s,%s\n",id,p1.getX(),p1.getY()));
                        id++;
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                finally {
                    try{bout.close();}catch (Exception e){}
                }
            }
        }


        for(int i=0;i<k;i++){
            Collection<MovingObject> ps = generateMOs(p, Util.meters2degree(1000),Util.meters2degree(5000),100);

            File file = new File("output/mos-"+100+"-"+i);
            BufferedWriter bout = null;
            try {
                bout = new BufferedWriter(new FileWriter(file));
                int id = 0;
                for(MovingObject p1 : ps){
                    bout.write(String.format("%s,%s,%s\n",p1.getId(),p1.getPoint().getX(),p1.getPoint().getY()));
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            finally {
                try{bout.close();}catch (Exception e){}
            }
        }

    }


}
