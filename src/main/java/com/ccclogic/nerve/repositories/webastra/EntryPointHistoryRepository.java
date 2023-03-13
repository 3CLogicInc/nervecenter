package com.ccclogic.nerve.repositories.webastra;

import com.ccclogic.nerve.entities.webastra.EntryPointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface EntryPointHistoryRepository extends JpaRepository<EntryPointHistory, Integer>, PagingAndSortingRepository<EntryPointHistory, Integer> {
    List<EntryPointHistory> findAllByNumber(String entrypoint);
}
