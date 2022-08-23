package com.ccclogic.sailor.util.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntityApiResponse<T> {
    private T data;
    private Long timestamp;
}
