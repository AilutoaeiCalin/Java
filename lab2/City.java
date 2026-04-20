package lab2;

/**
 * Locatie de tip oras
 */

public final class City extends Location {

    private final int population;

    public City(String name, double x, double y, int population) {
        super(name, x, y);
        if (population < 0) throw new IllegalArgumentException("Invalid population.");
        this.population = population;
    }

    public int getPopulation() { return population; }

    @Override
    public String toString() {
        return super.toString().replace("}", ", population=" + population + "}");
    }
}