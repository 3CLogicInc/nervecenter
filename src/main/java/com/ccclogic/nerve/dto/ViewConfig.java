package com.ccclogic.nerve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.json.JSONObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewConfig {
    JSONObject subHeader;
    JSONObject body;
}
