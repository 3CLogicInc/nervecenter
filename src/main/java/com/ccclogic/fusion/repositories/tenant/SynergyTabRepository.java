package com.ccclogic.fusion.repositories.tenant;

import com.ccclogic.fusion.entities.tenant.SynergyView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SynergyTabRepository extends JpaRepository<SynergyView, Integer> {

}
