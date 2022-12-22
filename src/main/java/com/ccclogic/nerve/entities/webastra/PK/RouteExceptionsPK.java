package com.ccclogic.nerve.entities.webastra.PK;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class RouteExceptionsPK implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "exception_id")
    private Integer exceptionId;

    @Column(name = "route_id")
    private String routeId;
}
