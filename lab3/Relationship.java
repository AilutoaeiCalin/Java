package lab3;
public class Relationship {
    public enum Type { KNOWS, WORKS_FOR }

    private final Type type;
    private final String detail;

    public Relationship(Type type, String detail) {
        this.type = type;
        this.detail = detail;
    }

    public Type getType() { return type; }
    public String getDetail() { return detail; }

    @Override
    public String toString() {
        return type + "(" + detail + ")";
    }
}