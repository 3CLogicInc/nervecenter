package com.ccclogic.nerve.entities.webastra;

import com.ccclogic.nerve.entities.webastra.PK.CallcenterRoutePK;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "nc_callcenter_routes")
@IdClass(CallcenterRoutePK.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenantRoute {
    @Id
    @Column(name = "callcenter_id")
    private Integer callcenterId;

    @Id
    @Column(name = "route_id")
    private Integer routeId;

    @Column(name = "created_at", insertable = false, updatable = false)
    private java.sql.Timestamp createdAt;

    @Column(name = "created_by")
    private Integer createdBy;
}
