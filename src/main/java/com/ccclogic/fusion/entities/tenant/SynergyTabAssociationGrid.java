package com.ccclogic.fusion.entities.tenant;


import com.ccclogic.fusion.entities.tenant.identifier.SynergyTabAssociationGridId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "synergy_view_association_grid")
@AllArgsConstructor
@NoArgsConstructor
public class SynergyTabAssociationGrid {

    @Id
    SynergyTabAssociationGridId id;

}

