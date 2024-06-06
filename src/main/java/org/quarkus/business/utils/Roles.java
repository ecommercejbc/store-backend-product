package org.quarkus.business.utils;


import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "roles")
public class Roles extends PanacheMongoEntity {
    public String name;
}