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
                    p.setInfluencerId(product.getInfluencerId());
                    p.setCategoryName(product.getCategoryName());
                    return productRepository.update(p);
                }).onItem().ifNull().failWith(new IllegalArgumentException("No se encontró ningun producto con el id proporcionado"));
    }

    @Override
    public Uni<List<Product>> productsByInfluencerId(String influencerId) {
        return productRepository.find("influencerId", influencerId).list();
    }

    @Override
    public Uni<Product> getProductByInfluencerAndSlug(String influencerId, String slug) {
        return productRepository.findProductByInfluencerAndSlug(influencerId, slug);
    }

    @Override
    public Uni<List<Product>> getProductsByInfluencerAndCategoryName(String influencerId, String categoryName) {
        return productRepository.findProductsByInfluencerAndCategoryName(influencerId, categoryName);
    }
}
