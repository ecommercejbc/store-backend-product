package org.quarkus.business.respository;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Singleton;
import org.quarkus.business.document.Product;

@Singleton
public class ProductRepository implements ReactivePanacheMongoRepository<Product> {

    //crear query para listar por productType:
    //Multi<Product> findByProductType(String productType);

}
