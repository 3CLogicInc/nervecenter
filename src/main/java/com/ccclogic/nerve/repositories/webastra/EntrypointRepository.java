package com.ccclogic.nerve.repositories.webastra;

import com.ccclogic.nerve.entities.webastra.Entrypoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
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

    List<Entrypoint> findAllByIdIn(List<Integer> entryPointIds);

    @Query("SELECT case when count(ep) > 0 then true else false end from Entrypoint ep where ep.entrypoint=:entrypoint and ep.channel=:channel")
    boolean existsByEntrypointAndChannel(String entrypoint, String channel);

    @Query("SELECT e FROM Entrypoint e " +
            "LEFT JOIN e.callcenter c " +
            "WHERE " +
            "   LOWER(e.entrypoint) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "   LOWER(e.channel) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "   LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Entrypoint> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT e FROM Entrypoint e " +
            "LEFT JOIN e.callcenter c " +
            "WHERE " +
            "   (LOWER(e.entrypoint) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "   LOWER(e.channel) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "   LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND e.status = :status")
    List<Entrypoint> searchByKeywordAndStatus(@Param("keyword") String keyword, @Param("status") String status);
}
