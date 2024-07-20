package org.quarkus.business.respository;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Singleton;
import org.quarkus.business.document.Product;

import java.util.List;

@Singleton
public class ProductRepository implements ReactivePanacheMongoRepository<Product> {

    public Uni<List<Product>> findProductsByInfluencerAndCategoryName(String influencerId, String categoryName){
        return find("influencerId = ?1 and categoryName = ?2", influencerId, categoryName).list();
    }

    public Uni<Product> findProductByInfluencerAndSlug(String influencerId, String slug){
        return find("{ 'influencerId': { $regex: ?1 }, 'slug': { $regex: ?2, $options: 'i' } }",
                "^" + influencerId + "$", "^" + slug + "$").firstResult();
    }
}
