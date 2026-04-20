package com.Kapa.model;


import java.util.Objects;

public class Street implements Comparable<Street> {

    private final String name;
    private final Intersection first;
    private final Intersection second;
    private final double length;

    public Street(String name, Intersection first, Intersection second, double length) {
        this.name = name;
        this.first = first;
        this.second = second;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public Intersection getFirst() {
        return first;
    }

    public Intersection getSecond() {
        return second;
    }

    public double getLength() {
        return length;
    }

    public boolean connects(Intersection intersection) {
        return first.equals(intersection) || second.equals(intersection);
    }

    public Intersection getOtherEnd(Intersection intersection) {
        if (first.equals(intersection)) return second;
        if (second.equals(intersection)) return first;
        throw new IllegalArgumentException();
    }

    @Override
    public int compareTo(Street other) {
        return Double.compare(this.length, other.length);
    }

    @Override
    public String toString() {
        return name + ": " + first.getName() + " <-> " + second.getName() + " (" + length + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Street street)) return false;

        boolean sameEnds =
                (first.equals(street.first) && second.equals(street.second)) ||
                        (first.equals(street.second) && second.equals(street.first));

        return sameEnds && Double.compare(length, street.length) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, length);
    }
}