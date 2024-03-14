package com.ccclogic.nerve.entities.webastra;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "role_permission")
@Data
public class RolePermission {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "permission_id")
    private Integer permissionId;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "created_by")
    private Integer createdById;
}

