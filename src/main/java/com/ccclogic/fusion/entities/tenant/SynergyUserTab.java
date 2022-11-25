package com.ccclogic.fusion.entities.tenant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "synergy_user_tab")
@AllArgsConstructor
@NoArgsConstructor
public class SynergyUserTab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    Integer userId;
    Integer viewId;
    Integer associationId;
    String filtersConfig;
    String projectionConfig;
    Long createdAt;
}
