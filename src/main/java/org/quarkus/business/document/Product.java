package org.quarkus.business.document;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@MongoEntity(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends ReactivePanacheMongoEntity {
    public String name;
    public String shortDescription;
    public String description;
    public List<Integer> reviewRatings;
    public List<RelatedProduct> relatedProducts;
    public List<CrossSellProduct> crossSellProducts;
    public Media productThumbnail;
    public List<Media> productGalleries;
    public Media productMetaImage;
    public Media sizeChartImage;
    public List<Review> reviews;
    public List<Category> categories;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RelatedProduct {
        public ObjectId id;
        public String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CrossSellProduct {
        public ObjectId id;
        public String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Media {
        public ObjectId  id;
        public String collectionName;
        public String name;
        public String fileName;
        public String mimeType;
        public String disk;
        public String conversionsDisk;
        public String size;
        public ObjectId createdById;
        public String createdAt;
        public String updatedAt;
        public String originalUrl;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Review {
        public ObjectId id;
        public ObjectId productId;
        public ObjectId consumerId;
        public ObjectId storeId;
        public ObjectId reviewImageId;
        public Integer rating;
        public String description;
        public String createdAt;
        public String updatedAt;
        public String deletedAt;
        public Media reviewImage;
        public Consumer consumer;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Consumer {
        public ObjectId id;
        public String name;
        public String email;
        public String countryCode;
        public Integer phone;
        public ObjectId profileImageId;
        public String systemReserve;
        public Integer status;
        public ObjectId createdById;
        public String emailVerifiedAt;
        public String createdAt;
        public String updatedAt;
        public String deletedAt;
        public Integer ordersCount;
        public Role role;
        public Point point;
        public Wallet wallet;
        public List<Address> address;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Role {
            public Integer id;
            public String name;
            public String guardName;
            public String systemReserve;
            public String createdAt;
            public String updatedAt;
            public Pivot pivot;

            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Pivot {
                public ObjectId modelId;
                public ObjectId roleId;
                public String modelType;
            }
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Point {
            public ObjectId id;
            public ObjectId consumerId;
            public Double balance;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Wallet {
            public ObjectId id;
            public ObjectId consumerId;
            public Double balance;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Address {
            public ObjectId id;
            public String title;
            public ObjectId userId;
            public String street;
            public String city;
            public String pincode;
            public String country;
            public String createdAt;
            public String updatedAt;
            public String deletedAt;
        }
    }
}
