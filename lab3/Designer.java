package lab3;

import java.time.LocalDate;

public class Designer extends Person {
    private final String tool;

    public Designer(String id, String name, LocalDate birthDate, String city, String tool) {
        super(id, name, birthDate, city);
        this.tool = tool;
    }

    public String getTool() {
        return tool;
    }
}