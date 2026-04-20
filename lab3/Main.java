package lab3;

import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        SocialNetwork sn = new SocialNetwork();

        Company c1 = new Company("C1", "Amazon", "Tech");
        Company c2 = new Company("C2", "UiPath", "Automation");

        Person p1 = new Programmer("P1", "Calin", LocalDate.of(2005, 4, 4), "Iasi", "Java");
        Person p2 = new Designer("P2", "Ana", LocalDate.of(2004, 10, 10), "Iasi", "Figma");
        Person p3 = new Person("P3", "Mihai", LocalDate.of(2003, 1, 2), "Bucuresti");

        sn.addProfile(c1);
        sn.addProfile(c2);
        sn.addProfile(p1);
        sn.addProfile(p2);
        sn.addProfile(p3);

        sn.connect(p1, p2, "colleagues");
        sn.connect(p2, p3, "friends");
        sn.employ(p1, c2, "Intern");
        sn.employ(p3, c2, "Junior");
        sn.employ(p2, c1, "UI Designer");


        List<Profile> all = new ArrayList<>(sn.getProfiles());
        all.sort(Comparator.comparing(Profile::getName, String.CASE_INSENSITIVE_ORDER));

        System.out.println("=== Sorted by NAME (Comparator) ===");
        for (Profile pr : all) System.out.println(pr.getName());

        System.out.println("\n=== Network (sorted by IMPORTANCE) ===");
        sn.printNetwork();
    }
}