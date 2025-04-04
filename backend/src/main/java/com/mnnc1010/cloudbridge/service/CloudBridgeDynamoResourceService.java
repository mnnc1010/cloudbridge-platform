package com.mnnc1010.cloudbridge.service;

import com.mnnc1010.cloudbridge.model.CloudBridgeResource;
import com.mnnc1010.cloudbridge.repository.CloudBridgeDynamoResourceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Service class for managing CloudBridgeResource entities in AWS DynamoDB.
 *
 * <p>This service encapsulates business logic and interacts with the DynamoDB repository to
 * perform operations such as retrieving all resources and creating new ones.</p>
 */
@Service
public class CloudBridgeDynamoResourceService {

    private final CloudBridgeDynamoResourceRepository repository;
    private final DynamoDbClient dynamoDbClient;

    /**
     * Constructor for initializing the DynamoDbClient and the repository.
     *
     * @param region    The AWS region where the DynamoDB table is hosted (e.g., "us-east-2").
     * @param tableName The name of the DynamoDB table.
     */
    public CloudBridgeDynamoResourceService(@Value("${aws.dynamodb.region}") String region,
                                            @Value("${aws.dynamodb.tableName}") String tableName) {
        this.dynamoDbClient = DynamoDbClient.builder()
                .region(Region.of(region))
                .build();
        this.repository = new CloudBridgeDynamoResourceRepository(dynamoDbClient, tableName);
    }

    /**
     * Retrieves all CloudBridgeResource items from the DynamoDB table.
     *
     * @return a list of CloudBridgeResource objects.
     */
    public List<CloudBridgeResource> getAllResources() {
        return repository.findAll();
    }

    /**
     * Creates a new CloudBridgeResource in the DynamoDB table.
     *
     * <p>If the resource does not have an ID, a unique identifier is generated before saving.
     * This ensures that the required partition key for DynamoDB is present.</p>
     *
     * @param resource the CloudBridgeResource object to create.
     * @return the created CloudBridgeResource, including its generated identifier.
     */
    public CloudBridgeResource createResource(CloudBridgeResource resource) {
        // Generate a UUID if the id is missing or empty
        if (resource.getId() == null || resource.getId().trim().isEmpty()) {
            resource.setId(UUID.randomUUID().toString());
            System.out.println("Generated new UUID: " + resource.getId());
        }

        // Set dateInserted and dateModified to current timestamp if not provided.
        String now = Instant.now().toString();
        if (resource.getDateInserted() == null || resource.getDateInserted().trim().isEmpty()) {
            resource.setDateInserted(now);
        }
        if (resource.getDateModified() == null || resource.getDateModified().trim().isEmpty()) {
            resource.setDateModified(now);
        }

        return repository.save(resource);
    }
}