package com.ccclogic.nerve.entities.webastra.PK;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class CallcenterRoutePK implements Serializable {
    @Id
    @Column(name = "callcenter_id")
    private Integer callcenterId;

    @Id
    @Column(name = "route_id")
    private Integer routeId;
}
