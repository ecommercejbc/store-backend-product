package org.quarkus.business.controller;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.openapitools.client.model.ProductRequestDTO;
import org.quarkus.business.service.ProductService;


import jakarta.ws.rs.*;
import org.quarkus.business.document.Product;
import org.quarkus.business.validator.ProductRequestValidator;

import java.util.List;

@Path("/api/v1/product")

public class ProductController {


    @Inject
    ProductService productService;

    @Inject
    ProductRequestValidator productRequestValidator;

    @POST
    @RolesAllowed("Admin")
    public Uni<Response> save(@Valid ProductRequestDTO productRequestDTO) {
        return productService.insert(productRequestDTO)
                .onItem().transform(product -> Response.status(Response.Status.CREATED)
                        .entity("Producto creado correctamente.").build())
                .onFailure().recoverWithItem(throwable -> Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error al crear el producto.").build());
    }
    @GET
    public Uni<List<Product>> getAllProducts() {
        return productService.findAll().collect().asList();
    }

    @GET
    @Path("/{id}")
    public Uni<Response> getProductById(@PathParam("id") String id) {
        return productService.findById(id)
                .onItem().transform(product -> {
                    if (product != null) {
                        return Response.ok(product).build();
                    } else {
                        return Response.status(Response.Status.NOT_FOUND).entity("Producto no encontrado.").build();
                    }
                });
    }


    @PUT
    @Path("/{id}")
    @RolesAllowed("ROLE_VENDOR")
    public Uni<Response> updateProduct(@PathParam("id") String id, @Valid ProductRequestDTO productRequestDTO) {
        // Validar la solicitud del producto
        //Set<ConstraintViolation<ProductRequestDTO>> violations = validator.validate(productRequestDTO);
       // if (!violations.isEmpty()) {
       //     throw new ValidationException("La solicitud del producto no es válida.");
       // }

        return productService.update(id, productRequestDTO)
                .onItem().transform(product -> {
                    if (product != null) {
                        return Response.ok(product).build();
                    } else {
                        return Response.status(Response.Status.NOT_FOUND).entity("Producto no encontrado.").build();
                    }
                });
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ROLE_VENDOR")
    public Uni<Response> deleteProduct(@PathParam("id") String id) {
        return productService.delete(id)
                .onItem().transform(deleted -> {
                    if (deleted) {
                        return Response.noContent().build();
                    } else {
                        return Response.status(Response.Status.NOT_FOUND).entity("Producto no encontrado.").build();
                    }
                });
    }
}
