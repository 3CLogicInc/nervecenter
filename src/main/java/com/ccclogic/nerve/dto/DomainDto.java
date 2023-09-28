package com.ccclogic.nerve.dto;

import lombok.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DomainDto {
    private Long id;
    private String domain;
    private java.sql.Timestamp createdAt;
}

