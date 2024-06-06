package org.quarkus.business.utils;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import org.quarkus.business.utils.User;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class ExampleResource {


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }

    @GET
    @Path("/me")
    //@RolesAllowed({Roles.USER, Roles.SERVICE})
    public User me(@javax.ws.rs.core.Context SecurityContext securityContext) {
        return User.find("email", securityContext.getUserPrincipal().getName()).firstResult();
    }

    @GET
    @Path("/admin")
    //@RolesAllowed(Roles.ADMIN)
    public String adminTest() {
        return "If you see this text as a user, then something is broke";
    }

    @GET
    @Path("/void")
    //@RolesAllowed({Roles.SERVICE})
    public String nothing() {
        return "This method should always return 403";
    }

}