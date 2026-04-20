package catalog.command;


import catalog.exception.InvalidCommandArgumentsException;
import catalog.repository.CatalogRepository;

public class LoadCommand implements Command {
    private final CatalogRepository repository;
    private final String filePath;

    public LoadCommand(CatalogRepository repository, String filePath) throws InvalidCommandArgumentsException {
        if (filePath == null || filePath.isBlank()) {
            throw new InvalidCommandArgumentsException("File path for load catalog.command cannot be empty.");
        }
        this.repository = repository;
        this.filePath = filePath;
    }

    @Override
    public void execute() throws Exception {
        repository.loadFromJson(filePath);
        System.out.println("Catalog loaded successfully from: " + filePath);
    }
}