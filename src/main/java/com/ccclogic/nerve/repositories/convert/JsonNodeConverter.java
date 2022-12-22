package com.ccclogic.nerve.repositories.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;

@Slf4j
public class JsonNodeConverter implements AttributeConverter<JsonNode, String> {
    @Override
    public String convertToDatabaseColumn(JsonNode attribute) {
        if (attribute == null) {
            log.warn("jsonNode input is null, returning null");
            return null;
        }

        String jsonNodeString = attribute.toPrettyString();
        return jsonNodeString;
    }

    @Override
    public JsonNode convertToEntityAttribute(String jsonNodeString) {
        if (StringUtils.isEmpty(jsonNodeString)) {
            log.warn("jsonNodeString input is empty, returning null");
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(jsonNodeString);
        } catch (JsonProcessingException e) {
            log.error("Error parsing jsonNodeString", e);
        }
        return null;
    }
}
