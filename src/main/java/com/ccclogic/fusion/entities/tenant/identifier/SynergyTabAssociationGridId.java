package com.ccclogic.fusion.entities.tenant.identifier;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public class SynergyTabAssociationGridId implements Serializable {
    @Id
    private Integer tabId;

    @Id
    private Integer associationGridTabId;
}
