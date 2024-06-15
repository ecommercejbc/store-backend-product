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
}