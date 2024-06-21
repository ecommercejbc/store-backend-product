package org.quarkus.business.service.impl;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.bson.types.ObjectId;
import org.quarkus.business.document.Product;
import org.quarkus.business.respository.ProductRepository;
import org.quarkus.business.service.ProductService;

import java.util.List;

@Singleton
public class ProductImpl implements ProductService {

    @Inject
    ProductRepository productRepository;

    @Override
    public Uni<Product> saveProduct(Product product) {
        return productRepository.persist(product);
    }

    @Override
    public Uni<List<Product>> listProducts() {
         return productRepository.listAll();
    }

    @Override
    public Uni<Product> getProduct(ObjectId id) {
        return productRepository.findById(id);
    }

    @Override
    public Uni<List<Product>> getProductBy(String userId, String categoryId) {
        String query = String.format("{ userId: {$regex: '^%s$'}, categoryId: {$regex: '^%s$'} }", userId, categoryId);
        return productRepository.find(query).list();
    }

    @Override
    public Uni<Product> deleteProduct(ObjectId id) {
        return productRepository.findById(id)
                .onItem().transformToUni(product -> productRepository.delete(product).onItem().transform(x -> product));
    }
}
