package com.ccclogic.nerve.entities.webastra;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "exception_domains")
public class ExceptionDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "domain")
    private String domain;

    @Column(name = "created_at")
    private java.sql.Timestamp createdAt;

    @Column(name = "created_by")
    private Integer createdBy;
}
