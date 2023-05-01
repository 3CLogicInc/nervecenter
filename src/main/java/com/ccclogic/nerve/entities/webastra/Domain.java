package com.ccclogic.nerve.entities.webastra;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "nc_domains")
public class Domain {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "domain")
    String domain;

    @Column(name = "created_at", insertable = false, updatable = false)
    private java.sql.Timestamp createdAt;
}
