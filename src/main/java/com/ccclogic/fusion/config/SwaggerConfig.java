package com.ccclogic.fusion.config;

import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OperationCustomizer operationCustomizer(){
        return ((operation, handlerMethod) -> {
            operation.addParametersItem(
                    new Parameter()
                            .in("header")
                            .required(false)
                            .name("Authorization"));
            operation.addParametersItem(
                    new Parameter()
                            .in("path")
                            .required(false)
                            .name("centerId"));

            return operation;
        });
    }
}
