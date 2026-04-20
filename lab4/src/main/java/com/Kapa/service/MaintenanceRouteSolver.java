package com.Kapa.service;

import com.Kapa.model.*;

import java.util.*;

public class MaintenanceRouteSolver {

    public Tour approximateMetricTour(City city, Intersection start) {

        List<Intersection> route = new ArrayList<>(city.getSortedIntersections());

        route.add(start);

        double length = 0;

        for (int i = 0; i < route.size() - 1; i++) {

            length += route.get(i).distanceTo(route.get(i + 1));
        }

        return new Tour(route, length);
    }
}