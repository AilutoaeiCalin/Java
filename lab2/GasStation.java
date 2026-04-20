package lab2;

/**
 * Locatie de tip benzinarie
 */
public final class GasStation extends Location {

    private final double gasPrice;

    public GasStation(String name, double x, double y, double gasPrice) {
        super(name, x, y);
        if (gasPrice < 0) throw new IllegalArgumentException("Invalid gas price.");
        this.gasPrice = gasPrice;
    }

    public double getGasPrice() { return gasPrice; }

    @Override
    public String toString() {
        return super.toString().replace("}", ", gasPrice=" + gasPrice + "}");
    }
}
