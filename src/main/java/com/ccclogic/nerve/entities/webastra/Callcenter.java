package com.ccclogic.nerve.entities.webastra;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "callcenters")
public class Callcenter {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    String name;

    @Column(name = "owner")
    Integer ownerId;

    @Column(name = "is_demo")
    Boolean isDemo;

    @Column(name = "status")
    Integer status;

    @Column(name = "release")
    String release;
}
