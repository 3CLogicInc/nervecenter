package com.ccclogic.nerve.entities.webastra;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "entrypoint")
public class Entrypoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "entrypoint")
    String entrypoint;

    @Column(name = "channel")
    String channel;

    @Column(name = "ccid")
    Integer ccId;

    @Column(name = "updated_by")
    Integer updatedById;

    @Column(name = "created_by")
    Integer createdById;
    
    @Column(name = "status")
    String status;

    @Column(name = "flow_id")
    Integer flowId;

    @Column(name = "flow_name")
    String flowName;

    @Column(name = "created_on")
    String createdOn;

    @Column(name = "created_at", updatable = false, insertable = false)
    Date createdAt;

    @Column(name = "updated_at", updatable = false, insertable = false)
    Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "ccid", insertable = false, updatable = false)
    private Callcenter callcenter;

    @ManyToOne
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    private UserMinimal createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by", insertable = false, updatable = false)
    private UserMinimal updatedBy;
}
