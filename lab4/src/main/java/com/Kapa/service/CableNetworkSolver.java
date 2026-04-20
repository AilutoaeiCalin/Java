package com.Kapa.service;



import com.Kapa.model.*;
import org.jgrapht.graph.*;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;

import java.util.*;

public class CableNetworkSolver {

    public CableSolution getMinimumCableSolution(City city) {

        SimpleWeightedGraph<Intersection, DefaultWeightedEdge> graph =
                new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        city.getIntersections().forEach(graph::addVertex);

        for (Street s : city.getStreets()) {

            DefaultWeightedEdge e = graph.addEdge(s.getFirst(), s.getSecond());
            graph.setEdgeWeight(e, s.getLength());
        }

        KruskalMinimumSpanningTree<Intersection, DefaultWeightedEdge> mst =
                new KruskalMinimumSpanningTree<>(graph);

        List<Street> solution = new ArrayList<>();

        for (DefaultWeightedEdge e : mst.getSpanningTree().getEdges()) {

            Intersection a = graph.getEdgeSource(e);
            Intersection b = graph.getEdgeTarget(e);

            city.findStreetBetween(a, b).ifPresent(solution::add);
        }

        return new CableSolution(solution);
    }

    public List<CableSolution> getBestCableSolutions(City city, int k) {

        List<CableSolution> list = new ArrayList<>();

        list.add(getMinimumCableSolution(city));

        return list;
    }
}