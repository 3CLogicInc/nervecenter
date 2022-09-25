package com.ccclogic.fusion.util;

import com.ccclogic.fusion.util.response.BulkEntityApiResponse;
import com.ccclogic.fusion.util.response.EntityApiResponse;
import com.ccclogic.fusion.util.response.ResponseFieldExtractor;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResponseGeneratorFactory {

    private static Logger logger = LoggerFactory.getLogger(ResponseGeneratorFactory.class);

    public static Object generate(Object response, List<String> fields) throws IllegalAccessException {

        if (response instanceof List || response instanceof Set) {
            response = getResponseEntityFromCollection(response, fields);
        } else if (response instanceof Page) {
            logger.debug("I am instance of page, I should be marked accordingly");
            response = getResponseEntityFromPage(response, fields);
        } else if (response instanceof EntityApiResponse) {
            logger.debug("I am instance of EntityApiResponse, I should be presented accordingly");
            response = new ResponseEntity(response, HttpStatus.OK);
        } else if (response instanceof ResponseEntity) {
            logger.debug("I am instance of ResponseEntity, I should be presented accordingly");
            response = getResponseEntityFromResponseEntity(response, fields);
        } else {
            response = getResponseEntityFromEntity(response, fields);
        }
        return response;
    }

    private static EntityApiResponse getResponseEntityFromEntity(Object response, List<String> fields) throws IllegalAccessException {
        EntityApiResponse entityApiResponse = EntityApiResponse
                .builder()
                .data(response)
                .timestamp(DateTime.now().toInstant().getMillis())
                .build();

        if (!fields.isEmpty()) {
            Map<String, Object> extractedKeyMap = ResponseFieldExtractor.extractKey(response, fields);
            entityApiResponse.setData(extractedKeyMap);
        }

        return entityApiResponse;
    }

    private static ResponseEntity getResponseEntityFromResponseEntity(Object response, List<String> fields) throws IllegalAccessException {
        ResponseEntity _responseEntity = (ResponseEntity) response;

        if (!_responseEntity.hasBody()) return _responseEntity;

        EntityApiResponse entityApiResponse = EntityApiResponse
                .builder()
                .data(_responseEntity.getBody())
                .timestamp(DateTime.now().toInstant().getMillis())
                .build();

        if (!fields.isEmpty()) {
            Map<String, Object> extractedKeyMap = ResponseFieldExtractor.extractKey(response, fields);
            entityApiResponse.setData(extractedKeyMap);
        }


        return new ResponseEntity(entityApiResponse, _responseEntity.getStatusCode());
    }

    private static BulkEntityApiResponse getResponseEntityFromPage(Object response, List<String> fields) throws IllegalAccessException {
        Page _tmp = (Page) response;
        BulkEntityApiResponse bulkEntityApiResponse = BulkEntityApiResponse.builder()
                .data(_tmp.getContent())
                .isFirst(_tmp.isFirst())
                .isLast(_tmp.isLast())
                .totalPages(_tmp.getTotalPages())
                .totalRecords(_tmp.getTotalElements())
                .timestamp(DateTime.now().toInstant().getMillis())
                .build();
        if (!fields.isEmpty()) {
            List extractedKeys = ResponseFieldExtractor.extractKeys(_tmp.getContent(), fields);
            bulkEntityApiResponse.setData(extractedKeys);
        }


        return bulkEntityApiResponse;
    }

    public static BulkEntityApiResponse getResponseEntityFromCollection(Object response, List<String> fields) throws IllegalAccessException {
        Collection _tmp = (Collection) response;
        BulkEntityApiResponse bulkEntityApiResponse = BulkEntityApiResponse.builder()
                .data(_tmp)
                .isFirst(true)
                .isLast(true)
                .totalPages(1)
                .totalRecords((long)_tmp.size())
                .timestamp(DateTime.now().toInstant().getMillis())
                .build();

        if (!fields.isEmpty()) {
            List extractedFieldList = ResponseFieldExtractor.extractKeys(_tmp, fields);
            bulkEntityApiResponse.setData(extractedFieldList);
        }

        return bulkEntityApiResponse;
    }
}
