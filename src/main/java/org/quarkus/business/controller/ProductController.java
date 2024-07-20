package org.quarkus.business.controller;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.openapitools.client.model.ProductRequestDTO;
import org.openapitools.client.model.ProductResponseDTO;
import org.quarkus.business.config.ProductMapper;
import org.quarkus.business.response.ResponseUtil;
import jakarta.ws.rs.*;
import org.quarkus.business.document.Product;
import org.quarkus.business.service.ProductService;
import org.quarkus.business.validator.ProductRequestValidator;
import java.util.List;

import java.util.HashMap;

@Path("/v1/product")
public class ProductController {
    @Inject
    ProductService productService;

    @Inject
    ProductMapper productMapper;

    @Inject
    ProductRequestValidator productRequestValidator;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> listProducts() {
        return productService.listProducts()
                .onItem().transformToUni( products -> Uni.createFrom().item(products.stream().map(
                        product -> productMapper.toProductResponseDTO(product)).toList()))
                .onItem().transform(ResponseUtil::buildResponseList)
                .onFailure().recoverWithItem(ResponseUtil::handleError);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> saveProduct(ProductResponseDTO productRequestDTO) {
        HashMap<String, List<String>> validationErrors = productRequestValidator.validate(productRequestDTO);
        if (!validationErrors.isEmpty()) {
            return Uni.createFrom().item(ResponseUtil.buildResponseHeaders(validationErrors));
        }

        Product product = productMapper.toProduct(productRequestDTO);

        return productService.saveProduct(product)
                .map(product1 -> productMapper.toProductResponseDTO(product))
                .onItem().transform(ResponseUtil::buildResponseObject)
                .onFailure().recoverWithItem(ResponseUtil::handleError);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> deleteProduct(@PathParam("id") String id) {
        return productService.deleteProduct(new ObjectId(id))
                .map(product -> productMapper.toProductResponseDTO(product))
                .onItem().transform(ResponseUtil::buildResponseObject)
                .onFailure().recoverWithItem(ResponseUtil::handleError);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> updateProduct(@PathParam("id") String id, ProductResponseDTO productRequestDTO) {
        HashMap<String, List<String>> validationErrors = productRequestValidator.validate(productRequestDTO);
        if(!validationErrors.isEmpty()) {
            return Uni.createFrom().item(ResponseUtil.buildResponseHeaders(validationErrors));
        }

        Product product = productMapper.toProduct(productRequestDTO);

        return productService.updateProduct(new ObjectId(id), product)
                .map(product1 -> productMapper.toProductResponseDTO(product))
                .onItem().transform(ResponseUtil::buildResponseObject)
                .onFailure().recoverWithItem(ResponseUtil::handleError);
    }

    @GET
    @Path("influencerId/{influencerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> productsByInfluencerId(@PathParam("influencerId") String influencerId) {
        return productService.productsByInfluencerId(influencerId)
                .onItem().transformToUni( products -> Uni.createFrom().item(products.stream().map(
                        product -> productMapper.toProductResponseDTO(product)).toList()))
                .onItem().transform(ResponseUtil::buildResponseList)
                .onFailure().recoverWithItem(ResponseUtil::handleError);
    }

    @GET
    @Path("/detail/{influencerId}/{slug}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getProductByUserAndSlug(@PathParam("influencerId") String influencerId, @PathParam("slug") String slug) {
        return productService.getProductByInfluencerAndSlug(influencerId, slug)
                .map(product -> productMapper.toProductResponseDTO(product))
                .onItem().transform(ResponseUtil::buildResponseObject)
                .onFailure().recoverWithItem(ResponseUtil::handleError);
    }

    @GET
    @Path("/influencerId/{influencerId}/category/{categoryName}")
    public Uni<Response> getProductsInfluencerIdByCategoryName(@PathParam("influencerId") String influencerId, @PathParam("categoryName") String categoryName){
        return productService.getProductsByInfluencerAndCategoryName(influencerId, categoryName)
                .onItem().transformToUni(products -> Uni.createFrom().item(products.stream()
                        .map(product -> productMapper.toProductResponseDTO(product)).toList()))
                .onItem().transform(ResponseUtil::buildResponseList)
                .onFailure().recoverWithItem(ResponseUtil::handleError);
    }
}
