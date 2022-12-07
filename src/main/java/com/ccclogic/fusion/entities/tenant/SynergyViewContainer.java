package com.ccclogic.fusion.entities.tenant;

import com.ccclogic.fusion.repositories.convert.JsonNodeConverter;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@Table(name = "synergy_view_container")
@AllArgsConstructor
@NoArgsConstructor
public class SynergyViewContainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "config", columnDefinition = "json")
    @Convert(converter = JsonNodeConverter.class)
    JsonNode config;

    @OneToMany
    @JoinTable(
            name = "synergy_container_view_mapping",
            joinColumns = @JoinColumn(name = "container_id"),
            inverseJoinColumns = @JoinColumn(name = "view_id", insertable = false, updatable = false)
    )
    Set<SynergyView> views;

    @Column(name = "created_at")
    Timestamp createdAt;
    @Column(name = "updated_at")
    Timestamp updatedAt;
}
