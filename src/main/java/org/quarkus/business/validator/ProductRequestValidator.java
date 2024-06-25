package org.quarkus.business.validator;

import jakarta.enterprise.context.Dependent;
import org.openapitools.client.model.ProductResponseDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Dependent
public class ProductRequestValidator {

    public HashMap<String, List<String>> validate(ProductResponseDTO productResponseDTO) {
        HashMap<String, List<String>> errors = new HashMap<>();

        // Validar el campo 'name'
        List<String> nameErrors = new ArrayList<>();
        if (productResponseDTO.getName() == null || productResponseDTO.getName().isEmpty()) {
            nameErrors.add("Name is required.");
        }
        if (productResponseDTO.getName() != null && productResponseDTO.getName().length() > 50) {
            nameErrors.add("Name must be less than 50 characters.");
        }
        // Puedes añadir más validaciones según sea necesario

        if (!nameErrors.isEmpty()) {
            errors.put("name", nameErrors);
        }

        // Validar el campo 'description'
        List<String> descriptionErrors = new ArrayList<>();
        if (productResponseDTO.getDescription() == null || productResponseDTO.getDescription().isEmpty()) {
            descriptionErrors.add("Description is required.");
        }
        if (productResponseDTO.getDescription() != null && productResponseDTO.getDescription().length() > 200) {
            descriptionErrors.add("Description must be less than 200 characters.");
        }
        // Ejemplo de validación adicional: longitud máxima
        // Puedes añadir más validaciones según sea necesario

        if (!descriptionErrors.isEmpty()) {
            errors.put("description", descriptionErrors);
        }

        // Validar otros campos según tus requisitos

        return errors;
    }

}
