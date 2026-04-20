package lab3;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
public class Person implements Profile, Comparable<Person>
{
    private final String id;
    private final String name;
    private final LocalDate birthDate;
    private final Map<Profile,Relationship> relations = new HashMap<>(); //relatiile catre alte "profile"
    private final String city;
    public Person(String id, String name, LocalDate birthDate, String city) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.city = city;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public String getCity() {
        return city;
    }
    public Map<Profile,Relationship> getRelations() {
        return Collections.unmodifiableMap(relations);
    }
    public int getImportance(){
        return relations.size();
    }
    public void addRelation(Profile other, Relationship rel) {
        relations.put(other, rel);
    }
    @Override
    public int compareTo(Person other) {
        return this.name.compareToIgnoreCase(other.name);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return id.equals(person.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public String toString() {
        return "Person{name='" + name + "', id='" + id + "', imp=" + getImportance() + "}";
    }

}
