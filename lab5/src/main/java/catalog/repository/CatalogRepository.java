package catalog.repository;



import com.fasterxml.jackson.databind.ObjectMapper;
import catalog.exception.InvalidCatalogException;
import catalog.model.Catalog;
import catalog.model.Resource;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CatalogRepository {
    private final Catalog catalog;

    public CatalogRepository(Catalog catalog) {
        this.catalog = catalog;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void addResource(Resource resource) {
        catalog.add(resource);
    }

    public void loadFromJson(String filePath) throws InvalidCatalogException {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Resource[] loadedResources = objectMapper.readValue(new File(filePath), Resource[].class);
            List<Resource> resourceList = Arrays.asList(loadedResources);

            catalog.getResources().clear();
            catalog.getResources().addAll(resourceList);
        } catch (IOException e) {
            throw new InvalidCatalogException("Could not load catalog from JSON file: " + filePath, e);
        }
    }
}