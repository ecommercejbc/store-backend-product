package org.quarkus.business.service;

import io.smallrye.mutiny.Uni;
import org.bson.types.ObjectId;
import org.quarkus.business.document.Product;

import java.util.List;

public interface ProductService {

    Uni<Product> saveProduct(Product product);
    Uni<List<Product>> listProducts();
    Uni<Product> getProduct(ObjectId id);
    Uni<Product> deleteProduct(ObjectId id);
    Uni<Product> updateProduct(ObjectId id, Product product);
    Uni<List<Product>> productsByInfluencerId(String influencerId);
    Uni<Product> getProductByInfluencerAndSlug(String influencerId, String slug);
    Uni<List<Product>> getProductsByInfluencerAndCategoryName(String influencerId, String categoryName);
}
