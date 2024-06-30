package org.quarkus.business.document;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;

@MongoEntity(collection = "products")
@RegisterForReflection
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends ReactivePanacheMongoEntity {
    public String name;
    public String slug;
    public String description;
    public String short_description;
    public double priceDiscount;
    public double priceOriginal;
    public String currency;
    public String urlImage;
    public String categoryId;
    public String userId;
}
