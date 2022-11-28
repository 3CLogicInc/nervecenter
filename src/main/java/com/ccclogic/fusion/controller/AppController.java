package com.ccclogic.fusion.controller;

import com.ccclogic.fusion.services.metadata.MetadataPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/app")
public class AppController {

    @Autowired
    MetadataPropertiesService metadataPropertiesService;

    @RequestMapping(method = RequestMethod.GET)
    public Map<String, String> getAppProperties() {
        Map<String, String> result = metadataPropertiesService.getAllProperties();

        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/synergy")
    public Map<String, String> getMetadataPropForSynergy() {
        Map<String, String> result = metadataPropertiesService.getPropForSynergy();

        return result;
    }


}
