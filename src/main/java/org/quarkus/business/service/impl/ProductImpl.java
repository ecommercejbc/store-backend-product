package org.quarkus.business.service.impl;

import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.openapitools.client.model.ProductRequestDTO;
import org.quarkus.business.document.Product;
import org.quarkus.business.respository.ProductRepository;
import org.quarkus.business.service.ProductService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;

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
        throw new IllegalArgumentException("El nombre del influencer no puede ser nulo o vacío");
        //return Uni.createFrom().failure(new IllegalArgumentException("El nombre del influencer no puede ser nulo o vacío"));
        //return productRepository.listAll();
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
