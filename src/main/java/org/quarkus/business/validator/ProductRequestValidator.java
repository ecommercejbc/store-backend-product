package org.quarkus.business.validator;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.openapitools.client.model.ProductRequestDTO;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Dependent
public class ProductRequestValidator {

    //@Inject
   // Validator validator;

    public boolean isValid(ProductRequestDTO productRequestDTO) {
        // Validar campos requeridos
        if (productRequestDTO.getName() == null || productRequestDTO.getName().isEmpty()) {
            return false;
        }
        if (productRequestDTO.getDescription() == null || productRequestDTO.getDescription().isEmpty()) {
            return false;
        }
        // Otras validaciones según tus requisitos
        // Por ejemplo, validar la longitud máxima de los campos, el formato, etc.

        // Si todas las validaciones pasan, la solicitud es válida
        return true;
    }

    public Set<ConstraintViolation<ProductRequestDTO>> validate(ProductRequestDTO productRequestDTO) {
       return null;// return validator.validate(productRequestDTO);
    }
}
