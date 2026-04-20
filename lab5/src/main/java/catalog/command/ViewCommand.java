package catalog.command;


import catalog.exception.InvalidCommandArgumentsException;
import catalog.model.Resource;
import catalog.repository.CatalogRepository;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;

public class ViewCommand implements Command {
    private final CatalogRepository repository;
    private final String resourceId;

    public ViewCommand(CatalogRepository repository, String resourceId) throws InvalidCommandArgumentsException {
        if (resourceId == null || resourceId.isBlank()) {
            throw new InvalidCommandArgumentsException("Resource id for view catalog.command cannot be empty.");
        }
        this.repository = repository;
        this.resourceId = resourceId;
    }

    @Override
    public void execute() throws Exception {
        Resource resource = repository.getCatalog().findById(resourceId);

        if (!Desktop.isDesktopSupported()) {
            throw new UnsupportedOperationException("Desktop API is not supported on this system.");
        }

        Desktop desktop = Desktop.getDesktop();

        if (resource.isWebResource()) {
            desktop.browse(new URI(resource.getLocation()));
        } else {
            desktop.open(new File(resource.getLocation()));
        }

        System.out.println("Resource opened: " + resource.getTitle());
    }
}