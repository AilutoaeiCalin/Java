package lab2;

/**
 * Locatie de tip aeroport
 */

public final class Airport extends Location {

    private final int terminals;

    public Airport(String name, double x, double y, int terminals) {
        super(name, x, y);
        if (terminals < 0) throw new IllegalArgumentException("Invalid terminals.");
        this.terminals = terminals;
    }

    public int getTerminals() { return terminals; }

    @Override
    public String toString() {
        return super.toString().replace("}", ", terminals=" + terminals + "}");
    }
}
