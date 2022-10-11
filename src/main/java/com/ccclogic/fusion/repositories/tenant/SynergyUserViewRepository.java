package com.ccclogic.fusion.repositories.tenant;

import com.ccclogic.fusion.entities.tenant.SynergyUserView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SynergyUserViewRepository extends JpaRepository<SynergyUserView, Integer> {
}
