package org.quarkus.business.document;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;

@MongoEntity(collection = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category extends ReactivePanacheMongoEntity {
    public String name;
    public String slug;
    public String description;
    public String id;
}





/*
   @BsonProperty("name")
    public String name;

    @BsonProperty("slug")
    public String slug;

    @BsonProperty("description")
    public String description;

    @BsonProperty("category_image")
    public Media categoryImage;

    @BsonProperty("category_icon")
    public Media categoryIcon;

    @BsonProperty("status")
    public Integer status;

    @BsonProperty("type")
    public String type;

    @BsonProperty("commission_rate")
    public Double commissionRate;

    @BsonProperty("parent_id")
    public Integer parentId;

    @BsonProperty("created_by_id")
    public String createdById;

    @BsonProperty("created_at")
    public String createdAt;

    @BsonProperty("updated_at")
    public String updatedAt;

    @BsonProperty("deleted_at")
    public String deletedAt;

    @BsonProperty("blogs_count")
    public Integer blogsCount;

    @BsonProperty("products_count")
    public Integer productsCount;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Media {
        public Integer id;
        public String collectionName;
        public String name;
        public String fileName;
        public String mimeType;
        public String disk;
        public String conversionsDisk;
        public String size;
        public String createdById;
        public String createdAt;
        public String updatedAt;
        public String originalUrl;
    }*/