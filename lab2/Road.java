package lab2;

/**
 *Drum care leaga 2 locatii
 */
public class Road {

    RoadType type;
    Location a;
    Location b;
    double length;
    int speedLimit;

    public Road(RoadType type, Location a, Location b, double length, int speedLimit) {
        this.type = type;
        this.a = a;
        this.b = b;
        this.length = length;
        this.speedLimit = speedLimit;
    }

    @Override
    public String toString() {
        return "Road " + type +
                " from " + a.getName() +
                " to " + b.getName() +
                " length=" + length +
                " speed=" + speedLimit;
    }

    /**
     * ca sa vad daca am duplicate
     *
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Road r)) return false;

        boolean sameEnds =
                (a.equals(r.a) && b.equals(r.b)) ||
                        (a.equals(r.b) && b.equals(r.a));

        return sameEnds &&
                type == r.type &&
                Double.compare(length, r.length) == 0 &&
                speedLimit == r.speedLimit;
    }
}
