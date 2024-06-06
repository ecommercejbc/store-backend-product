package org.quarkus.business.controller;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.openapitools.client.model.CategoryRequestDTO;
import org.quarkus.business.service.CategoryService;
import org.quarkus.business.document.Category;

import java.util.List;

@Path("/api/v1/category")
public class CategoryController {

    @Inject
    CategoryService categoryService;

    @POST
    @RolesAllowed("ROLE_VENDOR")
    public Uni<Response> save(CategoryRequestDTO categoryRequestDTO) {
        // Aquí puedes agregar la validación de la solicitud de la categoría si es necesario

        return categoryService.insert(categoryRequestDTO)
                .onItem().transform(category -> Response.status(Response.Status.CREATED)
                        .entity("Categoría creada correctamente.").build())
                .onFailure().recoverWithItem(throwable -> Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error al crear la categoría.").build());
    }

    @GET
    @RolesAllowed("User")
    public Uni<List<Category>> getAllCategories() {
        return categoryService.findAll().collect().asList();
    }

    @GET
    @Path("/{id}")
    public Uni<Response> getCategoryById(@PathParam("id") String id) {
        return categoryService.findById(id)
                .onItem().transform(category -> {
                    if (category != null) {
                        return Response.ok(category).build();
                    } else {
                        return Response.status(Response.Status.NOT_FOUND).entity("Categoría no encontrada.").build();
                    }
                });
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ROLE_VENDOR")
    public Uni<Response> updateCategory(@PathParam("id") String id, CategoryRequestDTO categoryRequestDTO) {
        // Aquí puedes agregar la validación de la solicitud de la categoría si es necesario

        return categoryService.update(id, categoryRequestDTO)
                .onItem().transform(category -> {
                    if (category != null) {
                        return Response.ok(category).build();
                    } else {
                        return Response.status(Response.Status.NOT_FOUND).entity("Categoría no encontrada.").build();
                    }
                });
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ROLE_VENDOR")
    public Uni<Response> deleteCategory(@PathParam("id") String id) {
        return categoryService.delete(id)
                .onItem().transform(deleted -> {
                    if (deleted) {
                        return Response.noContent().build();
                    } else {
                        return Response.status(Response.Status.NOT_FOUND).entity("Categoría no encontrada.").build();
                    }
                });
    }
}