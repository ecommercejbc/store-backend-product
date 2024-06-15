package org.quarkus.business.document;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.List;

@MongoEntity(collection = "products")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends ReactivePanacheMongoEntity {
    public String name;
    public String slug;
    public String description;
    public double priceDiscount;
    public double priceOriginal;
    public String currency;
    public String urlImage;
    public String categoryId;
    public String userId;
}
