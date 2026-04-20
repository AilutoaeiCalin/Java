package catalog.command;


import catalog.exception.InvalidCommandArgumentsException;
import catalog.model.Resource;
import catalog.repository.CatalogRepository;

public class AddCommand implements Command {

    private final CatalogRepository repository;
    private final Resource resource;

    public AddCommand(CatalogRepository repository, Resource resource)
            throws InvalidCommandArgumentsException {

        if (resource == null) {
            throw new InvalidCommandArgumentsException("Resource cannot be null.");
        }

        this.repository = repository;
        this.resource = resource;
    }

    @Override
    public void execute() {
        repository.addResource(resource);
        System.out.println("Resource added: " + resource.getTitle());
    }
}