package com.ccclogic.nerve.util.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BulkEntityApiResponse<T> {

    private Collection<T> data;
    private Boolean isFirst;
    private Boolean isLast;
    private Integer totalPages;
    private Long totalRecords;
    private Long timestamp;
}
