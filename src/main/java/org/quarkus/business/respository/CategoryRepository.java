package org.quarkus.business.respository;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.inject.Singleton;
import org.quarkus.business.document.Category;

@Singleton
public class CategoryRepository implements ReactivePanacheMongoRepository<Category> {

}