package com.ccclogic.fusion.repositories.metadata;

import com.ccclogic.fusion.entities.metadata.MetadataProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetadataPropertiesRepository extends PagingAndSortingRepository<MetadataProperties, Long>, JpaRepository<MetadataProperties, Long> {

    List<MetadataProperties> findByLoadOn(Integer loadOn);

    List<MetadataProperties> findByKeyIn(List<String> props);

}
