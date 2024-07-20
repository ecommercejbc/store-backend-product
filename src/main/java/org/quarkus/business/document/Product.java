package org.quarkus.business.document;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@MongoEntity(collection = "products")
@RegisterForReflection
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends ReactivePanacheMongoEntity {
    public ObjectId id;
    public String name;
    public String slug;
    public String description;
    public double priceDiscount;
    public double priceOriginal;
    public String currency;
    public String urlImage;
    public String influencerId;
    public String categoryName;
}
