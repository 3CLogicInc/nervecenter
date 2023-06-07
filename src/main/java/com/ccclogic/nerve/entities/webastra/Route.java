package com.ccclogic.nerve.entities.webastra;

import lombok.*;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "nc_routes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "domain")
    private Integer domainId;

    @Column(name = "is_default")
    private Boolean isDefault;

    @Column(name = "created_at", insertable = false, updatable = false)
    private java.sql.Timestamp createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private java.sql.Timestamp updatedAt;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @OneToMany(mappedBy = "routeId")
    private List<RouteExceptions> routeExceptions;

    @ManyToOne
    @JoinColumn(name = "domain", insertable = false, updatable = false)
    private Domain domain;

    @ManyToMany
    @JoinFormula(value = "(Select ncr.callcenter_id from nc_callcenter_routes ncr where ncr.route_id=id)")
    private List<Callcenter> callcenter;

}



