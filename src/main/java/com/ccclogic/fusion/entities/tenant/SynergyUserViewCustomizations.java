package com.ccclogic.fusion.entities.tenant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "synergy_user_view_customizations")
@AllArgsConstructor
@NoArgsConstructor
public class SynergyUserViewCustomizations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    Integer userId;
    Integer viewId;
    String rules;
    String actions;
    String actionType;
    Long createdAt;
}
