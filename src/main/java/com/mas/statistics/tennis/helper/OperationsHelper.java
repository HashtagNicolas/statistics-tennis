package com.mas.statistics.tennis.helper;

public class OperationsHelper {

    OperationsHelper(){throw new IllegalStateException("Helper Class");}
    public static Integer getMedian(Integer size){
        return (size%2 == 0) ? size/2 : (size+1)/2;
    }
    public static Double getIMC(double height, double weight){
        return weight / (height * height);
    }
    public static Double getRatio(double a, int b){
        return a / b;
    }
}
