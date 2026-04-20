package catalog.command;

import catalog.model.Resource;
import catalog.repository.CatalogRepository;

public class ListCommand implements Command {
    private final CatalogRepository repository;

    public ListCommand(CatalogRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute() {
        if (repository.getCatalog().getResources().isEmpty()) {
            System.out.println("Catalogul este gol.");
            return;
        }

        for (Resource resource : repository.getCatalog().getResources()) {
            System.out.println(resource);
        }
    }
}