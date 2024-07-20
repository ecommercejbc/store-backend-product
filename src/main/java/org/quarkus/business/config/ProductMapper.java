package org.quarkus.business.config;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.openapitools.client.model.ProductResponseDTO;
import org.quarkus.business.document.Product;

@ApplicationScoped
@Mapper(componentModel = "cdi")
@RegisterForReflection
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductResponseDTO toProductResponseDTO(Product product);
    Product toProduct(ProductResponseDTO productResponseDTO);

    default String map(ObjectId value) {
        return value != null ? value.toString() : null;
    }

    default ObjectId stringToObjectId(String value) {
        return value != null ? new ObjectId(value) : null;
    }
}
