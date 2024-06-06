package org.quarkus.business.service;


import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.openapitools.client.model.CategoryRequestDTO;
import org.quarkus.business.document.Category;

public interface CategoryService {

    Multi<Category> findAll();

    Uni<Category> insert(CategoryRequestDTO categoryRequestDTO);

    Uni<Category> findById(String id);

    Uni<Category> update(String id, CategoryRequestDTO categoryRequestDTO);

    Uni<Boolean> delete(String id);
}
