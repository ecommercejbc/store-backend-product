package org.quarkus.business.controller;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.openapitools.client.model.ProductRequestDTO;
import org.openapitools.client.model.ProductResponseDTO;
import org.quarkus.business.response.ResponseUtil;
import jakarta.ws.rs.*;
import org.quarkus.business.document.Product;
import org.quarkus.business.service.ProductService;
import org.quarkus.business.validator.ProductRequestValidator;
import java.util.List;

import java.util.HashMap;
import java.util.List;

@Path("/api/v1/product")
public class ProductController {


    @Inject
    ProductService productService;

    @Inject
    ModelMapper modelMapper;

    @Inject
    ProductRequestValidator productRequestValidator;

    @GET
    public Uni<Response> listProducts() {
        return productService.listProducts()
                .onItem().transformToUni( products -> Uni.createFrom().item(products.stream().map(
                        product -> modelMapper.map(product, ProductResponseDTO.class)).toList()))
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
    public Uni<Response> saveProduct(ProductResponseDTO productRequestDTO) {

        HashMap<String, List<String>> validationErrors = productRequestValidator.validate(productRequestDTO);
        if (!validationErrors.isEmpty()) {
            return Uni.createFrom().item(ResponseUtil.buildResponseHeaders(validationErrors));
        }

        Product product = modelMapper.map(productRequestDTO, Product.class);

        return productService.saveProduct(product)
                .map(product1 -> modelMapper.map(product1, ProductRequestDTO.class))
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

}
