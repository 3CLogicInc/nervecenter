package com.ccclogic.nerve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignedCallCentersDto {

        Integer CallcenterId;
        String CcName;
        Integer OwnerId;
        Boolean isDemo;
        Integer CcStatus;
        String Release;

    }
