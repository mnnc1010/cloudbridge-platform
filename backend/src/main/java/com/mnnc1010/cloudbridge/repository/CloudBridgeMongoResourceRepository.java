package com.mnnc1010.cloudbridge.repository;

import com.mnnc1010.cloudbridge.model.CloudBridgeResource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on CloudBridgeResource entities.
 *
 * <p>This interface extends {@link MongoRepository}, which provides many out-of-the-box
 * methods such as {@code save()}, {@code findAll()}, {@code findById()}, and {@code delete()}.
 * Custom query methods can be defined here following Spring Data's naming conventions.
 * </p>
 */
@Repository // Marks this interface as a Spring-managed bean for data access.
public interface CloudBridgeMongoResourceRepository extends MongoRepository<CloudBridgeResource, String> {
    // Additional custom query methods (if needed) can be defined here.
}