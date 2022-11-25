package com.ccclogic.fusion.repositories.tenant;

import com.ccclogic.fusion.entities.tenant.SynergyTabAssociationGrid;
import com.ccclogic.fusion.entities.tenant.identifier.SynergyTabAssociationGridId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SynergyTabAssociationGridRepository extends JpaRepository<SynergyTabAssociationGrid, SynergyTabAssociationGridId> {
}
