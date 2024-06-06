package org.quarkus.business.utils;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    TokenService service;


    @GET
    public String hello() {
        return "hello";
    }



    @POST
    @Path("/register")
    @Transactional
    public Response register(UserRequestDTO userRequest) {
        User existingUser = User.find("login", userRequest.login).firstResult(); // Query using MongoDB Panache
        if (existingUser != null) {
            // If the user already exists, check if the role is already assigned
            if (!existingUser.roles.contains(userRequest.rol)) {
                // If the role is not assigned, add it to the user's roles
                existingUser.roles.add(userRequest.rol);
                existingUser.persistOrUpdate(); // Update the user in the database
                return Response.status(Response.Status.OK)
                        .entity(existingUser)
                        .build();
            } else {
                // If the role is already assigned, return a message indicating that the role is already assigned
                return Response.status(Response.Status.CONFLICT)
                        .entity("Role " + userRequest.rol + " is already assigned to user " + userRequest.login)
                        .build();
            }
        } else {
            // If the user does not exist, create a new user with the provided details
            User newUser = new User();
            newUser.login = userRequest.login;
            newUser.email = userRequest.email;
            newUser.password = userRequest.password; // Remember to hash the password before storing it
            newUser.roles = new ArrayList<>();
            newUser.roles.add(userRequest.rol);
            newUser.persist(); // MongoDB Panache entity persistence
            return Response.status(Response.Status.CREATED)
                    .entity(newUser)
                    .build();
        }
    }

    @GET
    @Path("/login")
    public String login(@QueryParam("login") String login, @QueryParam("password") String password) {
        User existingUser = User.find("login", login).firstResult(); // Query using MongoDB Panache
        if (existingUser == null || !existingUser.password.equals(password)) {
            throw new WebApplicationException(String.valueOf(Response.status(404).entity("No user found or password is incorrect").build()));
        }
        return service.generateUserToken(existingUser);
    }
}