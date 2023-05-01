package com.ccclogic.nerve.entities.webastra;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "nc_route_exceptions")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RouteExceptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "country")
    private String country;

    @Column(name = "prefix")
    private String prefix;

    @Column(name = "route_id")
    private Integer routeId;

    @Column(name = "exception_domain_id")
    private Integer exceptionDomainId;

    @ManyToOne
    @JoinColumn(name = "exception_domain_id", insertable = false, updatable = false)
    private Domain domain;

    @Column(name = "created_at", insertable = false, updatable = false)
    private java.sql.Timestamp createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private java.sql.Timestamp updatedAt;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

}
