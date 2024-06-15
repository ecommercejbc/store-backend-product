package org.quarkus.business.service;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.bson.types.ObjectId;
import org.openapitools.client.model.ProductRequestDTO;
import org.quarkus.business.document.Product;

import java.util.List;

public interface ProductService {

    Uni<Product> saveProduct(Product product);
    Uni<List<Product>> listProducts();
    Uni<Product> getProduct(ObjectId id);
    Uni<List<Product>> getProductBy(String userId, String categoryId);
    Uni<Product> deleteProduct(ObjectId id);
}
