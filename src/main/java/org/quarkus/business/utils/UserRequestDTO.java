package org.quarkus.business.utils;


import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

import java.util.List;


public class UserRequestDTO {
    public String login;
    public String email;
    public String password;
    public String rol;
}