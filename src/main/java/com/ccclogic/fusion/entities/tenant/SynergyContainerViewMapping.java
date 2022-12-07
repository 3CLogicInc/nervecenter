package com.ccclogic.fusion.entities.tenant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "synergy_container_view_mapping")
@AllArgsConstructor
@NoArgsConstructor
public class SynergyContainerViewMapping {

    @Id @GeneratedValue
    private Integer id;

    @Column(name = "view_id")
    private Integer viewId;

    @Column(name = "container_id")
    private Integer containerId;

}
