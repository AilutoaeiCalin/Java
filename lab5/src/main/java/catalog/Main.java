package catalog;

import catalog.command.*;
import catalog.exception.InvalidResourceException;
import catalog.model.Catalog;
import catalog.model.Resource;
import catalog.repository.CatalogRepository;

public class Main {

    public static void main(String[] args) {

        try {

            Catalog catalog = new Catalog("My bibliography catalog");
            CatalogRepository repository = new CatalogRepository(catalog);

            Resource resource1 = new Resource(
                    "knuth67",
                    "The Art of Computer Programming",
                    "https://en.wikipedia.org/wiki/The_Art_of_Computer_Programming",
                    1967,
                    "Donald E. Knuth",
                    "Classic computer science book."
            );

            Resource resource2 = new Resource(
                    "jls25",
                    "The Java Language Specification",
                    "https://docs.oracle.com/javase/specs/jls/se25/jls25.pdf",
                    2025,
                    "James Gosling & others",
                    "Official Java language specification."
            );

            System.out.println("=== ADD COMMAND ===");
            new AddCommand(repository, resource1).execute();
            new AddCommand(repository, resource2).execute();

            System.out.println("\n=== LIST COMMAND ===");
            new ListCommand(repository).execute();

            System.out.println("\n=== REPORT COMMAND ===");
            new ReportCommand(repository, "report.html").execute();

            System.out.println("\n=== VIEW COMMAND ===");
            new ViewCommand(repository, "jls25").execute();

            System.out.println("\n=== LOAD COMMAND ===");
            new LoadCommand(repository, "src/main/resources/catalog.json").execute();

            System.out.println("\n=== LIST AFTER LOAD ===");
            new ListCommand(repository).execute();

        }
        catch (InvalidResourceException e) {
            System.err.println("Invalid resource: " + e.getMessage());
        }
        catch (Exception e) {
            System.err.println("Application error: " + e.getMessage());
            e.printStackTrace();
        }

    }
}