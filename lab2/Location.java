package lab2;
/**
 * Locatie generala
 */
public abstract sealed class Location permits City, Airport, GasStation {

    private final String name;
    private final double x;
    private final double y;

    protected Location(String name, double x, double y) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Invalid name.");
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public final String getName() { return name; }
    public final double getX() { return x; }
    public final double getY() { return y; }

    public final double distanceTo(Location other) {
        double dx = x - other.x;
        double dy = y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{name='" + name + "', x=" + x + ", y=" + y + "}";
    }

    /**
     * Conditia de egalitate
     */
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location other)) return false;
        return name.equalsIgnoreCase(other.name)
                && Double.compare(x, other.x) == 0
                && Double.compare(y, other.y) == 0;
    }
}
