package org.quarkus.business.service.impl;


import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.openapitools.client.model.CategoryRequestDTO;
import org.quarkus.business.document.Category;
import org.quarkus.business.respository.CategoryRepository;
import org.quarkus.business.service.CategoryService;
import org.quarkus.business.utils.Constants;

import java.util.List;
import java.sql.Timestamp;
import java.util.stream.Collectors;


@Singleton
public class CategoryImpl implements CategoryService {

    @Inject
    CategoryRepository categoryRepository;

    @Override
    public Uni<List<Category>> findAll() {
        return categoryRepository.listAll();
    }

    @Override
    public Uni<Category> findById(String name) {
        Document document = new Document().append("name", name);
        return categoryRepository.find(document).firstResult();
    }

    @Override
    public Multi<Category> userCategory(String userid) {
        String query = String.format("{ userId: { $regex:'^%s$'} }",userid);
        return categoryRepository.stream(query);
    }

    @Override
    public Uni<Category> insertCategory(Category category) {
        return categoryRepository.persist(category);
    }







    @Override
    public Uni<List<Category>> findInfluencers(String influencerName) {
        return categoryRepository.find(createSearchDocument(influencerName))
                .list()
                .onItem().transform(this::limitResults);// Toma los primeros 8 elementos
    }




    private Document createSearchDocument(String influencerName) {
        Document document = new Document();
        document.append("name", java.util.regex.Pattern.compile(influencerName, java.util.regex.Pattern.CASE_INSENSITIVE));
        return document;
    }

    private List<Category> limitResults(List<Category> users) {
        return users.stream().limit(Constants.MAX_RESULTS).collect(Collectors.toList());
    }

    @Override
    public Uni<Category> findById(ObjectId id) {
        return categoryRepository.findById(id);
    }

}