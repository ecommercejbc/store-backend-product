package org.quarkus.business.controller;


import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.quarkus.business.document.Category;
import org.bson.Document;
import org.quarkus.business.service.CategoryService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import org.quarkus.business.utils.ResponseUtil;


import java.awt.*;
import java.util.List;

@Path("/api/v1/category")
public class CategoryController {

    @Inject
    CategoryService categoryService;

    @GET
    public Uni<List<Category>> findAll() {
        return categoryService.findAll();
    }

    @GET
    @Path("/obtener/{name}")
    public Uni<Category> findById(@PathParam("name") String name) {
        Document query = new Document().append("name", name);
        return categoryService.findById(name);
    }


    @GET
    @Path("/userid/{userid}")
    public Multi<Category> userCategory(@PathParam("userid") String userid) {
        return categoryService.userCategory(userid);
    }

    @POST
    @Consumes
    public Uni<Category> insertCategory(Category category) {
        return categoryService.insertCategory(category);
    }


    @GET
    @Path("/influencers/{name}")
    public Uni<Response> findInfluencers6(@PathParam("name") String influencerName) {
        return categoryService.findInfluencers(influencerName)
                .onItem().transform(ResponseUtil::buildResponseList)
                .onFailure().recoverWithItem(ResponseUtil::handleError);
    }


    @GET
    @Path("/influencers/id/{id}")
    public Uni<Response> findUserById(@PathParam("id") String id) {
        return categoryService.findById(new ObjectId(id))
                .onItem().transform(ResponseUtil::buildResponseObject)
                .onFailure().recoverWithItem(ResponseUtil::handleError);
    }




}






/*
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
    }*/



