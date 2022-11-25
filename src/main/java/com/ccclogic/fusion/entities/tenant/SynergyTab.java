package com.ccclogic.fusion.entities.tenant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "synergy_tab")
@AllArgsConstructor
@NoArgsConstructor
public class SynergyTab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "header")
    String header;
    @Column(name = "body")
    String body;
    @Column(name = "filters")
    String filters;
    @Column(name = "projections")
    String projections;
    @Column(name = "secondary")
    Boolean secondary;
    @Column(name = "is_default")
    Boolean isDefault;

    @Column(name = "created_at")
    Timestamp createdAt;
    @Column(name = "updated_at")
    Timestamp updatedAt;
}
