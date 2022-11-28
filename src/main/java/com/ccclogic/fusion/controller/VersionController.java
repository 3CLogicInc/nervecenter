package com.ccclogic.fusion.controller;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/callcenters/{centerId}/info")
public class VersionController {

    @GetMapping
    public Map<String, Object> getVersion(@Value("${spring.application.name}") String name,
                                          @Value("${application.version}")String version){
        return ImmutableMap.of("name", name, "version", version);
    }
}
