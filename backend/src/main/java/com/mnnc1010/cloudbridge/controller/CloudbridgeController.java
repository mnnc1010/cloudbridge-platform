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

@CrossOrigin(origins = "http://localhost:3000")
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
     * Accepts a multipart/form-data request with file and metadata.
     *
     * File size must be greater than 1MB and less than or equal to 2MB.
     *
     * @param file the uploaded file
     * @param fileName the file name
     * @param fileType the file type (e.g., "application/pdf", "image/jpeg")
     * @param fileDescription description of the file
     * @param fileOwner owner of the file
     * @return a success message if uploaded, or a bad request error if validation fails
     */
    @PostMapping(value = "/mongo/resources", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadToMongo(
            @RequestParam("file") MultipartFile file,
            @RequestParam("fileName") String fileName,
            @RequestParam("fileType") String fileType,
            @RequestParam("fileDescription") String fileDescription,
            @RequestParam("fileOwner") String fileOwner) {

        long fileSize = file.getSize();
        long oneMB = 1048576;
        long twoMB = 2097152;

        // Validate file size for MongoDB: must be > 1MB and <= 2MB.
        if (fileSize <= oneMB || fileSize > twoMB) {
            return ResponseEntity.badRequest()
                    .body("For MongoDB, file size must be greater than 1MB and less than or equal to 2MB.");
        }

        CloudBridgeResource resource = new CloudBridgeResource();
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

        // Save the resource (dateInserted and dateModified are set by the service or database).
        mongoService.createResource(resource);
        // Return a simple success message.
        return ResponseEntity.ok("Successfully Uploaded to MongoDB");
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
     * Accepts a multipart/form-data request with file and metadata.
     *
     * File size must be less than 1MB.
     *
     * @param file the uploaded file
     * @param fileName the file name
     * @param fileType the file type
     * @param fileDescription description of the file
     * @param fileOwner owner of the file
     * @return a success message if uploaded, or a bad request error if validation fails
     */
    @PostMapping(value = "/dynamo/resources", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadToDynamo(
            @RequestParam("file") MultipartFile file,
            @RequestParam("fileName") String fileName,
            @RequestParam("fileType") String fileType,
            @RequestParam("fileDescription") String fileDescription,
            @RequestParam("fileOwner") String fileOwner) {

        long fileSize = file.getSize();
        long oneMB = 1048576;

        // Validate file size for DynamoDB: must be < 1MB.
        if (fileSize >= oneMB) {
            return ResponseEntity.badRequest()
                    .body("For DynamoDB, file size must be less than 1MB.");
        }

        CloudBridgeResource resource = new CloudBridgeResource();
        resource.setId(UUID.randomUUID().toString());
        resource.setFileName(fileName);
        resource.setFileType(fileType);
        resource.setFileStorage("DynamoDB");
        resource.setFileDescription(fileDescription);
        resource.setFileOwner(fileOwner);
        resource.setFileSize(fileSize);

        try {
            // Read file content as bytes.
            resource.setFileContent(file.getBytes());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error reading file content.");
        }

        // Save the resource.
        dynamoService.createResource(resource);
        // Return a success message.
        return ResponseEntity.ok("Successfully Uploaded to DynamoDB");
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