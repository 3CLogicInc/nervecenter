package com.ccclogic.nerve.config.advice;


import com.ccclogic.nerve.util.ResponseGeneratorFactory;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ResponseBodyConverter implements ResponseBodyAdvice {


    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        List<Annotation> annotations = Arrays.asList(methodParameter.getDeclaringClass().getAnnotations());
        return annotations.stream().anyMatch(annotation -> annotation.annotationType().equals(RestController.class));
    }

    @Override
    public Object beforeBodyWrite(Object response, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        System.out.println("rewriting response send by controller : " + response);
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
        String field = servletRequest.getParameter("fields");
        List<String> fields = new ArrayList<>();
        if (StringUtils.isNotBlank(field)) {
            fields = Arrays.stream(field.split(",")).map(String::trim).collect(Collectors.toList());
        }

        try {
            return ResponseGeneratorFactory.generate(response, fields);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return response;
    }
}
