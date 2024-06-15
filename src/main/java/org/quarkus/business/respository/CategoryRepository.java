package org.quarkus.business.respository;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Singleton;
import org.bson.types.ObjectId;
import org.quarkus.business.document.Category;

@Singleton
public class CategoryRepository implements ReactivePanacheMongoRepository<Category> {

}