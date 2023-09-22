package com.ccclogic.nerve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AssociationKafkaEventData implements Serializable  {

    private String sourceEntity;
    private String targetEntity;
    private Integer sourceId;
    private Integer targetId;
    private String action;
}
