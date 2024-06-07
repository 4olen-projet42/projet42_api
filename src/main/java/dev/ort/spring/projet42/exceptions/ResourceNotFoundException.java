package dev.ort.spring.projet42.exceptions;

public class ResourceNotFoundException extends Exception{

    public ResourceNotFoundException(Class<?> resourceType, Object resourceId) {
        super(resourceType.getSimpleName() + " " + resourceId + " not found");
    }

}
