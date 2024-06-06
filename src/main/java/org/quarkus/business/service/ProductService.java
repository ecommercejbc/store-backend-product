package org.quarkus.business.service;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.openapitools.client.model.ProductRequestDTO;
import org.quarkus.business.document.Product;

public interface ProductService {

    Multi<Product> findAll();

    Multi<Product> findAllByProviders();

    Uni<Product> insert(ProductRequestDTO productRequestDTO);

    Uni<Product> findById(String id);

    Uni<Product> update(String id, ProductRequestDTO productRequestDTO);

    Uni<Boolean> delete(String id);
}
