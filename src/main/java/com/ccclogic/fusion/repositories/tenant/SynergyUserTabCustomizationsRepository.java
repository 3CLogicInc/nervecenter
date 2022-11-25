package com.ccclogic.fusion.repositories.tenant;

import com.ccclogic.fusion.entities.tenant.SynergyUserTabCustomizations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SynergyUserTabCustomizationsRepository extends JpaRepository<SynergyUserTabCustomizations, Integer> {
}
