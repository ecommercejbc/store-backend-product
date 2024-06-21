package org.quarkus.business.config;

import jakarta.enterprise.context.ApplicationScoped;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

import javax.ws.rs.Produces;

@ApplicationScoped
public class ModelMapperConfig {


    @Produces
    @ApplicationScoped
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // Configuración adicional del ModelMapper si es necesario
        return modelMapper;
    }

    /*@Produces
    @ApplicationScoped
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Configuración para ignorar campos nulos
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true) // Habilita el emparejamiento de campos
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE) // Accede a los campos privados
                .setSkipNullEnabled(true); // Ignora los campos nulos durante el mapeo

        return modelMapper;
    }*/

}
