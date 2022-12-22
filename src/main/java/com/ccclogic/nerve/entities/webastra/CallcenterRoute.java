package com.ccclogic.nerve.entities.webastra;

import com.ccclogic.nerve.entities.webastra.PK.CallcenterRoutePK;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "nc_callcenter_routes")
@IdClass(CallcenterRoutePK.class)
public class CallcenterRoute {
    @Id
    @Column(name = "callcenter_id")
    private Integer callcenterId;

    @Id
    @Column(name = "route_id")
    private String routeId;

    @Column(name = "created_at")
    private java.sql.Timestamp createdAt;

    @Column(name = "created_by")
    private Integer createdBy;
}
