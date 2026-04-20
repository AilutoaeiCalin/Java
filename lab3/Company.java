package lab3;

import java.util.Objects;

public class Company implements Profile, Comparable<Company> {
    private final String id;
    private final String name;
    private final String industry;

    private int importance;

    public Company(String id, String name, String industry) {
        this.id = id;
        this.name = name;
        this.industry = industry;
        this.importance = 0;
    }

    public String getIndustry() { return industry; }

    @Override public String getId() { return id; }
    @Override public String getName() { return name; }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    @Override public int getImportance() { return importance; }

    @Override
    public int compareTo(Company other) {
        return this.name.compareToIgnoreCase(other.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company company)) return false;
        return id.equals(company.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Company{name='" + name + "', id='" + id + "', imp=" + importance + "}";
    }
}