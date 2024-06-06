package org.quarkus.business.service.impl;


import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.openapitools.client.model.CategoryRequestDTO;
import org.quarkus.business.document.Category;
import org.quarkus.business.respository.CategoryRepository;
import org.quarkus.business.service.CategoryService;

import java.sql.Timestamp;

@Singleton
public class CategoryImpl implements CategoryService {

    @Inject
    CategoryRepository categoryRepository;

    @Override
    public Multi<Category> findAll() {
        return categoryRepository.streamAll();
    }

    @Override
    public Uni<Category> insert(CategoryRequestDTO categoryRequestDTO) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Category category = new Category();
        // Aquí puedes asignar los valores de categoryRequestDTO a category
        // Por ejemplo: category.name = categoryRequestDTO.getName();

        return categoryRepository.persist(category).replaceWith(category);
    }

    @Override
    public Uni<Category> findById(String id) {
        return null;//categoryRepository.findById(id);
    }

    @Override
    public Uni<Category> update(String id, CategoryRequestDTO categoryRequestDTO) {
        // Implementa la lógica de actualización aquí
        return null;
    }

    @Override
    public Uni<Boolean> delete(String id) {
        // Implementa la lógica de eliminación aquí
        return null;
    }
}