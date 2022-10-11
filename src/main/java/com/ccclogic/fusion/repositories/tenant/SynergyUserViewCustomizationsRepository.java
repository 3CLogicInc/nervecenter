package com.ccclogic.fusion.repositories.tenant;

import com.ccclogic.fusion.entities.tenant.SynergyUserViewCustomizations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SynergyUserViewCustomizationsRepository extends JpaRepository<SynergyUserViewCustomizations, Integer> {
}
