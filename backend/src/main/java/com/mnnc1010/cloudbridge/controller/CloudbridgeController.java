package com.mnnc1010.cloudbridge.controller;

import com.mnnc1010.cloudbridge.model.CloudBridgeResource;
import com.mnnc1010.cloudbridge.service.CloudBridgeDynamoResourceService;
import com.mnnc1010.cloudbridge.service.CloudBridgeMongoResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * CloudbridgeController exposes RESTful endpoints for managing resources
 * in both MongoDB and AWS DynamoDB. This demonstrates how a single application
 * can work with multiple data stores by segregating endpoints and delegating to
 * different service layers.
 *
 * <p>
 * Endpoints provided:
 * <ul>
 *   <li><b>GET /api/greeting</b> - Returns a welcome message.</li>
 *   <li><b>GET /api/health</b> - Returns the health status of the application.</li>
 *   <li><b>MongoDB Endpoints</b>
 *     <ul>
 *       <li><b>GET /api/mongo/resources</b> - Retrieves all resources stored in MongoDB.</li>
 *       <li><b>POST /api/mongo/resources</b> - Creates a new resource in MongoDB.</li>
 *     </ul>
 *   </li>
 *   <li><b>DynamoDB Endpoints</b>
 *     <ul>
 *       <li><b>GET /api/dynamo/resources</b> - Retrieves all resources stored in AWS DynamoDB.</li>
 *       <li><b>POST /api/dynamo/resources</b> - Creates a new resource in AWS DynamoDB.</li>
 *     </ul>
 *   </li>
 *   <li><b>GET /api/resources</b> - Aggregates resources from both databases.</li>
 * </ul>
 * </p>
 */
@RestController // Marks this class as a REST controller, meaning that methods return data directly (usually as JSON).
@RequestMapping("/api") // Base URL for all endpoints in this controller.
public class CloudbridgeController {
    private final CloudBridgeMongoResourceService mongoService;
    private final CloudBridgeDynamoResourceService dynamoService;

    /**
     * Constructor for dependency injection of the two service layers.
     *
     * @param mongoService  The service handling MongoDB operations.
     * @param dynamoService The service handling AWS DynamoDB operations.
     */
    @Autowired
    public CloudbridgeController(CloudBridgeMongoResourceService mongoService,
                                 CloudBridgeDynamoResourceService dynamoService) {
        this.mongoService = mongoService;
        this.dynamoService = dynamoService;
    }

    /**
     * Endpoint to return a greeting message.
     *
     * @return A welcome message as plain text.
     */
    @GetMapping("/greeting")
    public String greeting() {
        return "Welcome to CloudBridge Platform!";
    }

    /**
     * Health check endpoint to verify the application is running.
     *
     * @return A JSON map indicating the health status of the application.
     */
    @GetMapping("/health")
    public Map<String, String> health() {
        return Collections.singletonMap("status", "UP");
    }

    // ---------------------- MongoDB Endpoints ---------------------- //

    /**
     * Retrieves all CloudBridge resources stored in MongoDB.
     *
     * @return A list of {@link CloudBridgeResource} objects retrieved from MongoDB.
     */
    @GetMapping("/mongo/resources")
    public List<CloudBridgeResource> getAllMongoResources() {
        return mongoService.getAllResources();
    }

    /**
     * Endpoint for uploading a file into MongoDB.
     * Expects a multipart/form-data request with file and metadata.
     *
     * @param file The uploaded file.
     * @param fileName The file name.
     * @param fileType The file type (MIME type, e.g., "application/pdf").
     * @param fileDescription A description of the file.
     * @param fileOwner The owner of the file.
     * @return The saved CloudBridgeResource if successful, or a bad request response if validation fails.
     */
    @PostMapping(value = "/mongo/resources", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadToMongo(
            @RequestParam("file") MultipartFile file,
            @RequestParam("fileName") String fileName,
            @RequestParam("fileType") String fileType,
            @RequestParam("fileDescription") String fileDescription,
            @RequestParam("fileOwner") String fileOwner) {

        // Validate file size: Must be > 1 MB and ≤ 2 MB for MongoDB upload.
        long fileSize = file.getSize();
        long oneMB = 1048576;
        long twoMB = 2097152;

        if (fileSize <= oneMB || fileSize > twoMB) {
            return ResponseEntity.badRequest()
                    .body("File size for MongoDB must be greater than 1MB and less than or equal to 2MB.");
        }

        // Create and populate the CloudBridgeResource model.
        CloudBridgeResource resource = new CloudBridgeResource();
        // Generate an ID if one is not provided.
        resource.setId(UUID.randomUUID().toString());
        resource.setFileName(fileName);
        resource.setFileType(fileType);
        resource.setFileStorage("MongoDB");
        resource.setFileDescription(fileDescription);
        resource.setFileOwner(fileOwner);
        resource.setFileSize(fileSize);
        try {
            // Read file content as bytes and set it in the resource.
            resource.setFileContent(file.getBytes());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error reading file content.");
        }
        // Call the service layer to save the resource.
        CloudBridgeResource savedResource = mongoService.createResource(resource);
        return ResponseEntity.ok(savedResource);
    }
    // ---------------------- DynamoDB Endpoints ---------------------- //

    /**
     * Retrieves all CloudBridge resources stored in AWS DynamoDB.
     *
     * @return A list of {@link CloudBridgeResource} objects retrieved from DynamoDB.
     */
    @GetMapping("/dynamo/resources")
    public List<CloudBridgeResource> getAllDynamoResources() {
        return dynamoService.getAllResources();
    }

    /**
     * Endpoint for uploading a file into DynamoDB.
     * Expects a multipart/form-data request with file and metadata.
     *
     * @param file The uploaded file.
     * @param fileName The file name.
     * @param fileType The file type.
     * @param fileDescription A description of the file.
     * @param fileOwner The owner of the file.
     * @return The saved CloudBridgeResource if successful, or a bad request response if validation fails.
     */
    @PostMapping(value = "/dynamo/resources", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadToDynamo(
            @RequestParam("file") MultipartFile file,
            @RequestParam("fileName") String fileName,
            @RequestParam("fileType") String fileType,
            @RequestParam("fileDescription") String fileDescription,
            @RequestParam("fileOwner") String fileOwner) {

        // Validate file size: Must be < 1 MB for DynamoDB upload.
        long fileSize = file.getSize();
        long oneMB = 1048576;
        if (fileSize >= oneMB) {
            return ResponseEntity.badRequest()
                    .body("File size for DynamoDB must be less than 1MB.");
        }

        // Create and populate the CloudBridgeResource model.
        CloudBridgeResource resource = new CloudBridgeResource();
        resource.setId(UUID.randomUUID().toString());
        resource.setFileName(fileName);
        resource.setFileType(fileType);
        resource.setFileStorage("DynamoDB");
        resource.setFileDescription(fileDescription);
        resource.setFileOwner(fileOwner);
        resource.setFileSize(fileSize);
        try {
            // Read file content as bytes and set it in the resource.
            resource.setFileContent(file.getBytes());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error reading file content.");
        }

        // Save the resource using the DynamoDB service.
        CloudBridgeResource savedResource = dynamoService.createResource(resource);
        return ResponseEntity.ok(savedResource);
    }

    // ---------------------- Aggregated Endpoint ---------------------- //

    /**
     * Retrieves all CloudBridge resources from both MongoDB and DynamoDB.
     *
     * <p>
     * This endpoint aggregates the resources retrieved from both data stores into a single list.
     * This is useful when you want to see a combined view of all resources in the system.
     * </p>
     *
     * @return A combined list of {@link CloudBridgeResource} objects from both databases.
     */
    @GetMapping("/resources")
    public List<CloudBridgeResource> getAllResources() {
        // Retrieve resources from MongoDB.
        List<CloudBridgeResource> mongoResources = mongoService.getAllResources();
        // Retrieve resources from DynamoDB.
        List<CloudBridgeResource> dynamoResources = dynamoService.getAllResources();
        // Combine the two lists.
        List<CloudBridgeResource> combinedResources = new ArrayList<>();
        combinedResources.addAll(mongoResources);
        combinedResources.addAll(dynamoResources);
        return combinedResources;
    }
}