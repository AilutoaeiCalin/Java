package com.Kapa.model;


import java.util.*;
import java.util.stream.Collectors;

public class CableSolution implements Comparable<CableSolution> {

    private final List<Street> streets;
    private final double totalCost;

    public CableSolution(Collection<Street> streets) {
        this.streets = new ArrayList<>(streets);
        this.totalCost = this.streets.stream()
                .mapToDouble(Street::getLength)
                .sum();
    }

    public List<Street> getStreets() {
        return streets;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public String canonicalSignature() {
        return streets.stream()
                .map(Street::toString)
                .collect(Collectors.joining("|"));
    }

    @Override
    public int compareTo(CableSolution o) {
        return Double.compare(this.totalCost, o.totalCost);
    }

    @Override
    public String toString() {
        return "Solution cost=" + totalCost + " streets=" + streets;
    }
}
