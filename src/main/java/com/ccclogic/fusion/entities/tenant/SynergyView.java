package com.ccclogic.fusion.entities.tenant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.json.JSONObject;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;

@Data
@Entity
@Table(name = "synergy_view")
@AllArgsConstructor
@NoArgsConstructor
public class SynergyView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "config", columnDefinition = "json")
    String config;

    @OneToMany
    @JoinTable(
            name = "synergy_view_tab",
            joinColumns = @JoinColumn(name = "view_id"),
            inverseJoinColumns = @JoinColumn(name = "tab_id", insertable = false, updatable = false)
    )
    Set<SynergyTab> tabs;

    @Column(name = "created_at")
    Timestamp createdAt;
    @Column(name = "updated_at")
    Timestamp updatedAt;
}
