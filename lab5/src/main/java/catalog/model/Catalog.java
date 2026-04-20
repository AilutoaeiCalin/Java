package catalog.model;
import catalog.exception.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Catalog {
    private String name;
    private final List<Resource> resources;

    public Catalog(String name) {
        this.name = name;
        this.resources = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void add(Resource resource) {
        resources.add(resource);
    }

    public Resource findById(String id) throws ResourceNotFoundException {
        for (Resource resource : resources) {
            if (resource.getId().equals(id)) {
                return resource;
            }
        }
        throw new ResourceNotFoundException("No resource found with id: " + id);
    }
}