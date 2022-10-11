package com.ccclogic.fusion.entities.tenant.identifier;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public class SynergyViewAssociationGridId implements Serializable {
    @Id
    private Integer viewId;

    @Id
    private Integer associationGridId;
}
