package com.mnnc1010.cloudbridge.service;

import com.mnnc1010.cloudbridge.model.CloudBridgeResource;
import com.mnnc1010.cloudbridge.repository.CloudBridgeMongoResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class that encapsulates the business logic for managing CloudBridge resources.
 *
 * <p>This class acts as an intermediary between the controller layer and the repository layer.
 * It uses {@link CloudBridgeMongoResourceRepository} for data access and provides methods for
 * retrieving and creating resources.
 * </p>
 */
@Service // Indicates that this class is a service component in the Spring context.
public class CloudBridgeMongoResourceService {

    private final CloudBridgeMongoResourceRepository repository;

    /**
     * Constructor for dependency injection of the repository.
     *
     * @param repository the repository used for data access.
     */
    @Autowired
    public CloudBridgeMongoResourceService(CloudBridgeMongoResourceRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all CloudBridge resources from the database.
     *
     * @return a list of all {@link CloudBridgeResource} objects.
     * @see CloudBridgeMongoResourceRepository#findAll()
     */
    public List<CloudBridgeResource> getAllResources() {
        return repository.findAll();
    }

    /**
     * Creates a new CloudBridge resource and saves it to the database.
     *
     * @param resource the resource to create.
     * @return the saved {@link CloudBridgeResource} with an assigned identifier.
     * @see CloudBridgeMongoResourceRepository#save(Object)
     */
    public CloudBridgeResource createResource(CloudBridgeResource resource) {
        return repository.save(resource);
    }

    // More business logic methods can be added here as needed.
}