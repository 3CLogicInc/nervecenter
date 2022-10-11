package com.ccclogic.fusion.entities.tenant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "synergy_view")
@AllArgsConstructor
@NoArgsConstructor
public class SynergyView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    String header;
    String body;
    String filters;
    String projections;
    Boolean secondary;

    @Column(name = "created_at")
    Timestamp createdAt;
    @Column(name = "updated_at")
    Timestamp updatedAt;
}
