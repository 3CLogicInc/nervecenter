package com.ccclogic.fusion.config.beans;

import com.ccclogic.fusion.entities.metadata.MetadataProperties;
import com.ccclogic.fusion.repositories.metadata.MetadataPropertiesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

@Component
@Slf4j
public class MetadataPropertyHolder {

    @Autowired
    MetadataPropertiesRepository metadataPropertiesRepository;

    public static Properties deploymentProperties = new Properties();
    public static Properties deploymentDefaults = new Properties();


    void init(){
        loadFromDatabase();
    }

    private void loadFromDatabase() {
        log.info("Loading properties while application startup.");
         metadataPropertiesRepository.findAll().forEach(mp -> {
            if(mp.getKey().startsWith("default.")){
                deploymentDefaults.put(mp.getKey(), mp.getValue());
            }else if(mp.getKey().startsWith("property.")){
                deploymentProperties.put(mp.getKey(), mp.getValue());
            }
        });
    }


    @Scheduled(fixedDelay = 180000)
    public void refresh(){
        log.info("Scheduled Reloading properties from the database.");
        List<MetadataProperties> metadataProperties = metadataPropertiesRepository.findByLoadOn(1);
        log.trace("Following properties fround to refresh : {}", metadataProperties);
        metadataProperties.forEach(mp -> {
            if(mp.getKey().startsWith("default.")){
                deploymentDefaults.put(mp.getKey(), mp.getValue());
            }else if(mp.getKey().startsWith("property.")){
                deploymentProperties.put(mp.getKey(), mp.getValue());
            }
        });
        log.trace("Metadata Properties loaded Successfully.");
    }
}
