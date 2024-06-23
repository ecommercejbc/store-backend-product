package org.quarkus.business.controller;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.quarkus.business.response.ResponseUtil;
import jakarta.ws.rs.*;
import org.quarkus.business.document.Product;
import org.quarkus.business.service.ProductService;
import org.quarkus.business.validator.ProductRequestValidator;
import java.util.List;

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
    public Uni<Response> saveProduct(Product product) {
        return productService.saveProduct(product)
                .onItem().transform(ResponseUtil::buildResponseObject)
                .onFailure().recoverWithItem(ResponseUtil::handleError);
    }

    @GET
    @Path("/{userId}/{categoryId}")
    public Uni<Response> getProductByUserAndCategory(@PathParam("userId") String userId, @PathParam("categoryId") String categoryId) {
        return productService.getProductBy(userId, categoryId)
                .onItem().transform(ResponseUtil::buildResponseList)
                .onFailure().recoverWithItem(ResponseUtil::handleError);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> deleteProduct(@PathParam("id") String id) {
        return productService.deleteProduct(new ObjectId(id)).onItem().transform(ResponseUtil::buildResponseObject)
                .onFailure().recoverWithItem(ResponseUtil::handleError);
    }

    @PUT
    @Path("/{id}")
    public Uni<Response> updateProduct(@PathParam("id") String id, Product product) {
        return productService.updateProduct(new ObjectId(id), product)
                .onItem().transform(ResponseUtil::buildResponseObject)
                .onFailure().recoverWithItem(ResponseUtil::handleError);
    }

}
