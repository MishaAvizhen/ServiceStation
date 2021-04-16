package service.exceptions;

public class ResourceAlreadyExists extends RuntimeException {

    public ResourceAlreadyExists(String message) {
        super("Resource with id: " + message+ " already exist");
    }
}
