package com.mnnc1010.cloudbridge.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

/**
 * Represents a resource within the CloudBridge Platform.
 *
 * <p>This domain model is used for both MongoDB and AWS DynamoDB. The MongoDB mapping is handled by
 * Spring Data annotations, while the DynamoDB Enhanced Client requires the {@code @DynamoDbBean} annotation.
 * The primary key for DynamoDB is defined with {@code @DynamoDbPartitionKey} on the {@code getId()} method.
 * </p>
 */
@Document(collection = "resources") // MongoDB mapping: stored in the "resources" collection.
@DynamoDbBean // DynamoDB mapping: indicates this is a DynamoDb bean.
public class CloudBridgeResource {

    @Id // MongoDB annotation for the document ID.
    private String id;
    private String name;
    private String type;
    private String description;

    /**
     * Gets the unique identifier for the resource.
     *
     * <p>
     * The {@code @DynamoDbPartitionKey} annotation marks this getter as the partition key for DynamoDB.
     * </p>
     *
     * @return the resource ID.
     */
    @DynamoDbPartitionKey // Indicates that this is the primary key in DynamoDB.
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the resource.
     *
     * @param id the resource ID to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name of the resource.
     *
     * @return the resource name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the resource.
     *
     * @param name the resource name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the type of the resource.
     *
     * @return the resource type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the resource.
     *
     * @param type the resource type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the description of the resource.
     *
     * @return the resource description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the resource.
     *
     * @param description the resource description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}