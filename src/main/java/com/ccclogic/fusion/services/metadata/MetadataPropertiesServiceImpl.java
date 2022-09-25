package com.ccclogic.fusion.services.metadata;

import com.ccclogic.fusion.entities.metadata.MetadataProperties;
import com.ccclogic.fusion.repositories.metadata.MetadataPropertiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MetadataPropertiesServiceImpl implements MetadataPropertiesService{

    @Autowired
    MetadataPropertiesRepository metadataPropertiesRepository;

    @Override
    public Map<String, String> getAllProperties() {
        Map<String, String> result = new HashMap<>();
        metadataPropertiesRepository.findAll().forEach((p -> result.put(p.getKey(), p.getValue())));
        return result;
    }

    @Override
    public Map<String, String> getPropForSynergy() {
        List<String> requiredProp = List.of(
                "signout_url",
                "ls_url",
                "secret_key_aggrid",
                "i18n",
                "domain_gateway",
                "footer_privacy_url",
                "footer_contact_url",
                "footer_terms_url",
                "footer_copyright_url",
                "footer_aboutus_url"
        );

        List<MetadataProperties> metadataProperties = metadataPropertiesRepository.findByKeyIn(requiredProp);

        Map<String, String> result = new HashMap<>();
        metadataProperties.stream().forEach((p -> result.put(p.getKey(), p.getValue())));
        return result;
    }




}
