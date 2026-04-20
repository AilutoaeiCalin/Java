package catalog.model;



import catalog.exception.InvalidResourceException;

public class Resource {
    private String id;
    private String title;
    private String location;
    private int year;
    private String author;
    private String description;

    public Resource() {
    }

    public Resource(String id, String title, String location, int year, String author, String description)
            throws InvalidResourceException {
        setId(id);
        setTitle(title);
        setLocation(location);
        setYear(year);
        setAuthor(author);
        setDescription(description);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws InvalidResourceException {
        if (id == null || id.isBlank()) {
            throw new InvalidResourceException("Resource id cannot be null or empty.");
        }
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws InvalidResourceException {
        if (title == null || title.isBlank()) {
            throw new InvalidResourceException("Resource title cannot be null or empty.");
        }
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) throws InvalidResourceException {
        if (location == null || location.isBlank()) {
            throw new InvalidResourceException("Resource location cannot be null or empty.");
        }
        this.location = location;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) throws InvalidResourceException {
        if (year < 0) {
            throw new InvalidResourceException("Resource year cannot be negative.");
        }
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) throws InvalidResourceException {
        if (author == null || author.isBlank()) {
            throw new InvalidResourceException("Resource author cannot be null or empty.");
        }
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? "" : description;
    }

    public boolean isWebResource() {
        return location.startsWith("http://") || location.startsWith("https://");
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", year=" + year +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}