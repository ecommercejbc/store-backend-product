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
        //throw new IllegalArgumentException("El nombre del influencer no puede ser nulo o vacío");
        //return Uni.createFrom().failure(new IllegalArgumentException("El nombre del influencer no puede ser nulo o vacío"));
        return productRepository.listAll();
    }

    @Override
    public Uni<Product> getProduct(ObjectId id) {
        return productRepository.findById(id);
    }

    @Override
    public Uni<List<Product>> getProductsByUserAndCategory(String userId, String categoryId) {
        String query = String.format("{ userId: {$regex: '^%s$'}, categoryId: {$regex: '^%s$'} }", userId, categoryId);
        return productRepository.find(query).list();
    }

    @Override
    public Uni<Product> deleteProduct(ObjectId id) {
        return productRepository.findById(id)
                .onItem().transformToUni(product -> productRepository.delete(product).onItem().transform(x -> product));
    }

    @Override
    public Uni<Product> updateProduct(ObjectId id, Product product) {
        return productRepository.findById(id)
                .onItem().ifNotNull().transformToUni(p ->{
                    p.setName(product.getName());
                    p.setSlug(product.getSlug());
                    p.setDescription(product.getDescription());
                    p.setPriceDiscount(product.getPriceDiscount());
                    p.setPriceOriginal(product.getPriceOriginal());
                    p.setCurrency(product.getCurrency());
                    p.setUrlImage(product.getUrlImage());
                    p.setCategoryId(product.getCategoryId());
                    p.setUserId(product.getUserId());
                    return productRepository.update(p);
                }).onItem().ifNull().failWith(new IllegalArgumentException("No se encontró ningun producto con el id proporcionado"));
    }

    @Override
    public Uni<List<Product>> productsByUserId(String userId) {
        return productRepository.find("userId", userId).list();
    }

    @Override
    public Uni<Product> getProductByUserAndSlug(String userId, String slug) {
        String query = String.format("{ userId: {$regex: '^%s$'}, slug: {$regex: '^%s$', $options: 'i'} }", userId, slug);
        return productRepository.find(query).firstResult();
    }
}
