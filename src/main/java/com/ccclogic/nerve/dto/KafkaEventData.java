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
public class KafkaEventData<T> implements Serializable  {

    private String id;
    private Integer version;
    private Timestamp timestamp;
    private String name;
    private Long ccId;
    private Map<String, String> service;
    private T data;
}
