package com.ccclogic.sailor.repositories.metadata;

import com.ccclogic.sailor.entities.metadata.MetadataProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetadataPropertiesRepository extends JpaRepository<MetadataProperties, Long> {
    
    List<MetadataProperties> findByLoadOn(Integer loadOn);

    @Query(value = "select * from  properties where `key` in (:props)", nativeQuery = true)
    List<MetadataProperties> findPropForSynergy(List<String> props);

}
