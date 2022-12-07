package com.ccclogic.fusion.entities.tenant;

import com.ccclogic.fusion.repositories.convert.JsonNodeConverter;
import com.fasterxml.jackson.databind.JsonNode;
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

    @Column(name = "header")
    @Convert(converter = JsonNodeConverter.class)
    JsonNode header;
    @Column(name = "body")
    @Convert(converter = JsonNodeConverter.class)
    JsonNode body;
    @Column(name = "filters")
    @Convert(converter = JsonNodeConverter.class)
    JsonNode filters;
    @Column(name = "projections")
    @Convert(converter = JsonNodeConverter.class)
    JsonNode projections;
    @Column(name = "secondary")
    Boolean secondary;
    @Column(name = "is_default")
    Boolean isDefault;

    @Column(name = "created_at")
    Timestamp createdAt;
    @Column(name = "updated_at")
    Timestamp updatedAt;
}
