package com.ccclogic.nerve.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BulkOperationDto {
    List<Integer> selectedListRecords;
    List<Map<String, Object>> mappedId;
}
