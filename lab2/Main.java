package lab2;

public class Main {

    public static void main(String[] args) {
        Problem p = new Problem();

        Location iasi = new City("Iasi", 0, 0, 300000);
        Location bacau = new City("Bacau", 10, 0, 170000);
        Location airport = new Airport("IAS Airport", 2, 3, 2);
        Location omv = new GasStation("OMV", 6, 1, 7.35);
        Location cluj = new City("Cluj", 100, 100, 320000);

        p.addLocation(iasi);
        p.addLocation(bacau);
        p.addLocation(airport);
        p.addLocation(omv);
        p.addLocation(cluj);

        p.addRoad(new Road(RoadType.COUNTRY, iasi, airport, 5.0, 50));
        p.addRoad(new Road(RoadType.EXPRESS, airport, omv, 4.5, 90));
        p.addRoad(new Road(RoadType.HIGHWAY, omv, bacau, 5.0, 130));

        System.out.println("Valid? " + p.isValid());
        System.out.println("Reach Iasi -> Bacau? " + p.canReach(iasi, bacau)); // true
        System.out.println("Reach Iasi -> Cluj? " + p.canReach(iasi, cluj));   // false
    }
}