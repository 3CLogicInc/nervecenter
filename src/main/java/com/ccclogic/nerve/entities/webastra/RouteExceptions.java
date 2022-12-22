package com.ccclogic.nerve.entities.webastra;

import com.ccclogic.nerve.entities.webastra.PK.RouteExceptionsPK;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "nc_route_exceptions")
@IdClass(RouteExceptionsPK.class)
public class RouteExceptions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "exception_id")
    private Integer exceptionId;

    @Column(name = "route_id")
    private String routeId;

    @Column(name = "domain")
    private String domain;

    @Column(name = "prefix")
    private String prefix;

    @Column(name = "created_at")
    private java.sql.Timestamp createdAt;

    @Column(name = "created_by")
    private Integer createdBy;
}
