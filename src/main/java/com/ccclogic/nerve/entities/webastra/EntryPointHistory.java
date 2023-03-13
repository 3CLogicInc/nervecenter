package com.ccclogic.nerve.entities.webastra;

import com.ccclogic.nerve.entities.common.DefaultFieldEventListener;
import com.ccclogic.nerve.entities.common.EntitySerializable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "number_history")
@EntityListeners(DefaultFieldEventListener.class)
public class EntryPointHistory implements EntitySerializable {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "number")
    private String number;

    @Column(name = "number_id")
    private Integer numberId;

    @Column(name = "activity_details")
    private String activityDetails;

    @Column(name = "created_at")
    private java.sql.Timestamp createdAt;

    @Column(name = "updated_at")
    private java.sql.Timestamp updatedAt;

    @Column(name = "created_by")
    private Integer createdById;

    @Column(name = "updated_by")
    private Integer updatedById;

    @ManyToOne
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    private UserMinimal createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by", insertable = false, updatable = false)
    private UserMinimal updatedBy;
}
