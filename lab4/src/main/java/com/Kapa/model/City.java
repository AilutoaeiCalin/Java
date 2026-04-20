package com.Kapa.model;


import java.util.*;
import java.util.stream.Collectors;

public class City {

    private final String name;
    private final Set<Intersection> intersections = new HashSet<>();
    private final LinkedList<Street> streets = new LinkedList<>();

    public City(String name) {
        this.name = name;
    }

    public void addIntersection(Intersection i) {
        intersections.add(i);
    }

    public void addStreet(Street s) {
        streets.add(s);
        intersections.add(s.getFirst());
        intersections.add(s.getSecond());
    }

    public Set<Intersection> getIntersections() {
        return intersections;
    }

    public LinkedList<Street> getStreets() {
        return streets;
    }

    public void sortStreetsByLength() {
        streets.sort(Comparator.naturalOrder());
    }

    public long countStreetsConnectedTo(Intersection i) {
        return streets.stream()
                .filter(s -> s.connects(i))
                .count();
    }

    public List<Street> getStreetsLongerThanAndJoiningBusyIntersections(double length) {
        return streets.stream()
                .filter(s -> s.getLength() > length)
                .filter(s ->
                        countStreetsConnectedTo(s.getFirst()) >= 3 &&
                                countStreetsConnectedTo(s.getSecond()) >= 3)
                .sorted()
                .collect(Collectors.toList());
    }

    public Optional<Street> findStreetBetween(Intersection a, Intersection b) {
        return streets.stream()
                .filter(s ->
                        (s.getFirst().equals(a) && s.getSecond().equals(b)) ||
                                (s.getFirst().equals(b) && s.getSecond().equals(a)))
                .findFirst();
    }

    public List<Intersection> getSortedIntersections() {
        return intersections.stream()
                .sorted()
                .toList();
    }

    @Override
    public String toString() {
        return "City: " + name + " intersections=" + intersections.size() + " streets=" + streets.size();
    }
}