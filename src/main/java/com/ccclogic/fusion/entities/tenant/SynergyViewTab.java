package com.ccclogic.fusion.entities.tenant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "synergy_view_tab")
@AllArgsConstructor
@NoArgsConstructor
public class SynergyViewTab {

    @Id @GeneratedValue
    private Integer id;

    @Column(name = "view_id")
    private Integer viewId;

    @Column(name = "tab_id")
    private Integer tabId;

}
