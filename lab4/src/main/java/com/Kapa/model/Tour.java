package com.Kapa.model;



import java.util.List;

public class Tour {

    private final List<Intersection> route;
    private final double totalLength;

    public Tour(List<Intersection> route, double totalLength) {
        this.route = route;
        this.totalLength = totalLength;
    }

    public List<Intersection> getRoute() {
        return route;
    }

    public double getTotalLength() {
        return totalLength;
    }

    @Override
    public String toString() {
        return "Tour length=" + totalLength + " route=" + route;
    }
}