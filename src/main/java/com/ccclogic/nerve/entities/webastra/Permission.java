package com.ccclogic.nerve.entities.webastra;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "permission")
@Data
public class Permission {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "permission_group")
    private String permissionGroup;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt;

    @Column(name = "created_by")
    private Integer createdById;

    @Column(name = "updated_by")
    private Integer updatedById;

}
