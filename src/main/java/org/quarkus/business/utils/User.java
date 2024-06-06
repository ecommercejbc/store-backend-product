package org.quarkus.business.utils;


import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

import java.util.List;


@MongoEntity(collection = "users")
public class User extends PanacheMongoEntity {
    public String login;
    public String email;
    public String password;
    public List<String> roles;
}