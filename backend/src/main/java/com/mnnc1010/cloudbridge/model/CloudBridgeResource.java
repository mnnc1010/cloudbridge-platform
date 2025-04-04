package com.mnnc1010.cloudbridge.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

/**
 * Represents a file resource in the CloudBridge Platform.
 *
 * <p>This model is used for both MongoDB and AWS DynamoDB. In addition to the original
 * fields (fileName, fileType, fileStorage, dateInserted, dateModified), we have added new fields:
 * <ul>
 *   <li>fileSize: The size of the file in bytes.</li>
 *   <li>fileOwner: The owner or uploader of the file.</li>
 * </ul>
 * These fields will be returned in the JSON response so that your Angular UI can display them.</p>
 */
@Document(collection = "resources")  // For MongoDB mapping.
@DynamoDbBean                        // For DynamoDB mapping.
public class CloudBridgeResource {

    @Id  // MongoDB document ID.
    private String id;
    private String fileName;
    private String fileType;
    private String fileStorage;
    private String fileDescription;
    private Long fileSize;
    private String fileOwner;
    private String dateInserted;
    private String dateModified;
    private byte[] fileContent;

    /**
     * The partition key for DynamoDB.
     *
     * @return the unique resource id.
     */
    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileStorage() {
        return fileStorage;
    }

    public void setFileStorage(String fileStorage) {
        this.fileStorage = fileStorage;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileOwner() {
        return fileOwner;
    }

    public void setFileOwner(String fileOwner) {
        this.fileOwner = fileOwner;
    }

    public String getDateInserted() {
        return dateInserted;
    }

    public void setDateInserted(String dateInserted) {
        this.dateInserted = dateInserted;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    /**
     * Gets the binary content of the file.
     * @return the file content as a byte array.
     */
    public byte[] getFileContent() {
        return fileContent;
    }

    /**
     * Sets the binary content of the file.
     * @param fileContent the file content as a byte array.
     */
    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

}