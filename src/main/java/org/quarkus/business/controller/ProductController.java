package org.quarkus.business.controller;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.quarkus.business.service.ProductService;


import jakarta.ws.rs.*;
import org.quarkus.business.document.Product;
import org.quarkus.business.utils.ResponseUtil;
import org.quarkus.business.validator.ProductRequestValidator;

@Path("/api/v1/product")
public class ProductController {


    @Inject
    ProductService productService;

    @Inject
    ProductRequestValidator productRequestValidator;

    @GET
    public Uni<Response> listProducts() {
        return productService.listProducts()
                .onItem().transform(ResponseUtil::buildResponseList)
                .onFailure().recoverWithItem(ResponseUtil::handleError);
    }

    @GET
    @Path("/{id}")
    public Uni<Response> getProduct(@PathParam("id") String id) {
        return productService.getProduct(new ObjectId(id))
                .onItem().transform(ResponseUtil::buildResponseObject)
                .onFailure().recoverWithItem(ResponseUtil::handleError);
    }

    @POST
    public Uni<Response> saveProduct(Product product){
        return productService.saveProduct(product)
                .onItem().transform(ResponseUtil::buildResponseObject)
                .onFailure().recoverWithItem(ResponseUtil::handleError);
    }

    @GET
    @Path("/{userId}/{categoryId}")
    public Uni<Response> getProductByUserAndCategory(@PathParam("userId") String userId, @PathParam("categoryId") String categoryId){
        return productService.getProductBy(userId, categoryId)
                .onItem().transform(ResponseUtil::buildResponseList)
                .onFailure().recoverWithItem(ResponseUtil::handleError);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> deleteProduct(@PathParam("id") String id){
        return productService.deleteProduct(new ObjectId(id)).onItem().transform(ResponseUtil::buildResponseObject)
                .onFailure().recoverWithItem(ResponseUtil::handleError);
    }





    /*@POST
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
    }*/
}
