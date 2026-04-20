package lab3;
import java.time.LocalDate;
public class Programmer extends Person {
    private final String favoriteLanguage;

    public Programmer(String id, String name, LocalDate birthDate, String city, String favoriteLanguage) {
        super(id, name, birthDate, city);
        this.favoriteLanguage = favoriteLanguage;
    }

    public String getFavoriteLanguage() {
        return favoriteLanguage;
    }
}