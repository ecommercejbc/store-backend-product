package org.quarkus.business.service;


import io.smallrye.mutiny.Multi;
import org.bson.types.ObjectId;
import org.openapitools.client.model.CategoryRequestDTO;
import org.quarkus.business.document.Category;
import io.smallrye.mutiny.Uni;
import org.quarkus.business.document.Category;
import org.quarkus.business.document.Product;

import java.util.List;

public interface CategoryService {

    Uni<List<Category>> findAll();
    Uni<Category> findById(String name);
    Multi<Category> userCategory(String userid);
    Uni<Category> insertCategory(Category category);
    Uni<List<Category>> findInfluencers(String influencerName);
    Uni<Category> findById(ObjectId id);


}










 /*Multi<Category> findAll();

    Uni<Category> insert(CategoryRequestDTO categoryRequestDTO);



    Uni<Category> update(String id, CategoryRequestDTO categoryRequestDTO);

    Uni<Boolean> delete(String id);*/