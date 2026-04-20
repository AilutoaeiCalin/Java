package com.Kapa.model;


import java.util.Objects;

public class Intersection implements Comparable<Intersection> {
    private final String name;
    private final double x;
    private final double y;

    public Intersection(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public Intersection(String name) {
        this(name, 0.0, 0.0);
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distanceTo(Intersection other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.hypot(dx, dy);
    }

    @Override
    public int compareTo(Intersection other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Intersection that)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name + "(" + String.format("%.1f", x) + "," + String.format("%.1f", y) + ")";
    }
}