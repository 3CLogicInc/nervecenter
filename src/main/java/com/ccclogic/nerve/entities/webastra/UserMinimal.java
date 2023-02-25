package com.ccclogic.nerve.entities.webastra;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "entities")
@Data
public class UserMinimal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    private String name;
    private String usernamealias;
}
