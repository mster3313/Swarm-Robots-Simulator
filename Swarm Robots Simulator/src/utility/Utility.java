
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import communication.messageData.aggregation.PulseFBData;
import java.awt.AlphaComposite;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 *
 * @author Nadun
 */
public class Utility {
    
    public static AlphaComposite alphaCompositeHidden = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0);
    public static AlphaComposite alphaCompositeVisible = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);

    private final static Random RANDOM = new Random();

    /**
     * Returns distance between two 2D points
     *
     * @param point1 first point
     * @param point2 second point
     * @return distance between points
     */
    public static double getDistance(Point2D point1, Point2D point2) {
        return getDistance(point1.getX(), point1.getY(), point2.getX(), point2.getY());
    }

    /**
     * Returns distance between two sets of coordinate
     *
     * @param x1 first x coordinate
     * @param y1 first y coordinate
     * @param x2 second x coordinate
     * @param y2 second y coordinate
     * @return distance between sets of coordinate
     */
    public static double getDistance(double x1, double y1, double x2, double y2) {
        // using long to avoid possible overflows when multiplying
        double dx = x2 - x1;
        double dy = y2 - y1;

        return Math.sqrt(dx * dx + dy * dy); // 10 times faster then previous line
    }

    /**
     * 
     * @param min
     * @param max
     * @return 
     */
    public static int randomInRange(int min, int max) {

        if (min > max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return RANDOM.nextInt((max - min) + 1) + min;
    }
    
    public static double getSlope(double x1, double y1, double x2, double y2) {
        // using long to avoid possible overflows when multiplying
        double dx = x1 - x2;
        double dy = y1 - y2;
        
        double toDegrees = Math.toDegrees(Math.atan(dy/dx));

        return toDegrees;
    }
    
    //---------------------------------aggregation functions------------------------------------------------------------
     public static double getMax(double[] inputArray){ 
        double pMax = inputArray[0];        
        for(int i=1;i < inputArray.length;i++){ 
            if(inputArray[i] > pMax){ 
                pMax = inputArray[i];                
            } 
        } 
        return pMax; 
    }    
    
     public static int getMaxProbSendersId(double[] inputArray, double pMax){ 
         int maxId = 0;
          for(int i=1;i < inputArray.length;i++){ 
              if(inputArray[i] == pMax) {
                  maxId = i;
              }
          }
          return maxId;
     }
     
    
    public static double getJoiningProb(int clusterSize) {
        double joiningProb;
        double o_max = 80;
        double robot_diameter = 40;
        double o_des = robot_diameter/4;
        double v = 86;
        double deltaT = 0.1;
        double arenaArea = 600000;
        double r_m = (1.20*o_des*Math.pow(clusterSize, 0.48))/2;
        
        if(clusterSize == 1) {
            joiningProb = (2*o_max*v*deltaT)/arenaArea;
        } else {
            joiningProb = (2*(o_max + r_m - (o_des/2))*v*deltaT)/arenaArea;
        }
        return joiningProb;
    } 
     
    public static double getLeavingProb(int clusterSize) {
        double leavingProb;
        double shrinkProb = 0.9; //use testing and adjust the value
        if(clusterSize < 6) {
            leavingProb = clusterSize * shrinkProb;
        } else {
            leavingProb = Math.PI* (1.20*Math.pow(clusterSize, 0.48) - 1) * shrinkProb;
        }
        return leavingProb;
    }
    
   //------------------------------------------------------------------------------------------------------------------- 
}
