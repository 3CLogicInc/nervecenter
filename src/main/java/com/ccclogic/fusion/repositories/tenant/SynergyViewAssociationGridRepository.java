package com.ccclogic.fusion.repositories.tenant;

import com.ccclogic.fusion.entities.tenant.SynergyViewAssociationGrid;
import com.ccclogic.fusion.entities.tenant.identifier.SynergyViewAssociationGridId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SynergyViewAssociationGridRepository extends JpaRepository<SynergyViewAssociationGrid, SynergyViewAssociationGridId> {
}
