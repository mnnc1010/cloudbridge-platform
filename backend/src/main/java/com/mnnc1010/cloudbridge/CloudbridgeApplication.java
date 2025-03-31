package com.mnnc1010.cloudbridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;



/**
 * The entry point for the CloudBridge Platform backend application.
 *
 * <p>
 * By excluding MongoAutoConfiguration and MongoDataAutoConfiguration, we prevent Spring Boot
 * from trying to connect to a MongoDB instance. This is necessary when switching to AWS DynamoDB.
 * </p>
 */
@SpringBootApplication
public class CloudbridgeApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudbridgeApplication.class, args);
    }
}