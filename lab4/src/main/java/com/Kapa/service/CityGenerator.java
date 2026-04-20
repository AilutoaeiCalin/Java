package com.Kapa.service;

import net.datafaker.Faker;
import com.Kapa.model.*;

import java.util.*;
import java.util.stream.IntStream;

public class CityGenerator {

    private final Faker faker = new Faker();
    private final Random random = new Random();

    public City generateDemoCityWith10Intersections() {

        City city = new City("DemoCity");

        List<Intersection> intersections = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> new Intersection(
                        "I" + i,
                        random.nextDouble(100),
                        random.nextDouble(100)
                ))
                .toList();

        intersections.forEach(city::addIntersection);

        for (int i = 0; i < intersections.size(); i++) {
            for (int j = i + 1; j < intersections.size(); j++) {

                if (random.nextDouble() < 0.4) {

                    Intersection a = intersections.get(i);
                    Intersection b = intersections.get(j);

                    city.addStreet(new Street(
                            faker.address().streetName(),
                            a,
                            b,
                            Math.round(a.distanceTo(b) * 100.0) / 100.0
                    ));
                }
            }
        }

        city.sortStreetsByLength();

        return city;
    }
}