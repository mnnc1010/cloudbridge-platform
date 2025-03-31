package com.mnnc1010.cloudbridge.repository;

import com.mnnc1010.cloudbridge.model.CloudBridgeResource;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository class for managing CloudBridgeResource entities in AWS DynamoDB.
 *
 * <p>
 * This class uses the DynamoDB Enhanced Client to perform CRUD operations on a DynamoDB table.
 * It maps the CloudBridgeResource class to the table specified by the provided table name.
 * </p>
 */
public class CloudBridgeDynamoResourceRepository {

    private final DynamoDbEnhancedClient enhancedClient;
    private final DynamoDbTable<CloudBridgeResource> resourceTable;

    /**
     * Constructor that initializes the DynamoDB Enhanced Client and maps the table.
     *
     * @param dynamoDbClient The low-level DynamoDbClient instance.
     * @param tableName      The name of the DynamoDB table.
     */
    public CloudBridgeDynamoResourceRepository(DynamoDbClient dynamoDbClient, String tableName) {
        // Build the enhanced client using the provided low-level client.
        this.enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
        // Map the CloudBridgeResource class to the DynamoDB table using the table schema.
        this.resourceTable = enhancedClient.table(tableName, TableSchema.fromBean(CloudBridgeResource.class));
    }

    /**
     * Retrieves all CloudBridgeResource items from the DynamoDB table.
     *
     * @return a list of CloudBridgeResource objects.
     */
    public List<CloudBridgeResource> findAll() {
        // Scan the entire table and collect the results into a list.
        return resourceTable.scan(ScanEnhancedRequest.builder().build())
                .items()
                .stream()
                .collect(Collectors.toList());
    }

    /**
     * Saves the provided CloudBridgeResource item to the DynamoDB table.
     *
     * @param resource the CloudBridgeResource object to save.
     * @return the same CloudBridgeResource object after saving.
     */
    public CloudBridgeResource save(CloudBridgeResource resource) {
        // Put (save) the item into the table.
        resourceTable.putItem(resource);
        return resource;
    }
}