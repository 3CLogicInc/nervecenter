package com.ccclogic.nerve.repositories.webastra;

import com.ccclogic.nerve.entities.webastra.Entrypoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface EntrypointRepository extends JpaRepository<Entrypoint, Integer>, PagingAndSortingRepository<Entrypoint, Integer> {

    List<Entrypoint> findAllByCallcenter(Integer ccId);

    List<Entrypoint> findAllByStatus(String status);

    List<Entrypoint> findAllByCcIdAndStatusIn(Integer ccId, List<String> status);

    List<Entrypoint> findAllByCcIdAndFlowIdAndStatusNot(Integer ccId, Integer flowId, String status);

    List<Entrypoint> findAllByCcIdAndFlowId(Integer ccId, Integer flowId);

    void removeAllByCcIdAndFlowId(Integer ccId, Integer flowId);

    List<Entrypoint> findAllEntryPointsByFlowId(Integer flowId);
}
