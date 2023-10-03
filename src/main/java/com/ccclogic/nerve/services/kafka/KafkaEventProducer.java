package com.ccclogic.nerve.services.kafka;

import com.ccclogic.nerve.dto.AssociationKafkaEventData;
import com.ccclogic.nerve.services.KafkaSender;
import com.ccclogic.nerve.dto.KafkaEventData;
import com.ccclogic.nerve.dto.AssignUnAssignRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.*;

@Component
@Slf4j
public class KafkaEventProducer {

    @Autowired
    KafkaSender kafkaSender;


    public void postAeEvent( String source, String target) {
        createAndSendAeEvent(source, target);
    }


    private void createAndSendAeEvent( String source, String target) {
        Long ccId = null;
        Map service = new HashMap<>();
      //  service.put("id",primaryEntityId.toString());
        service.put("name","Portal");

        KafkaEventData kafkaEventData = new KafkaEventData();
        kafkaEventData.setId(UUID.randomUUID().toString());
        kafkaEventData.setVersion(1);
        kafkaEventData.setTimestamp(new Timestamp(System.currentTimeMillis()));
        kafkaEventData.setName("PS_"+source+"_"+target+"_RELATIONSHIP_UPDATE");
        kafkaEventData.setCcId(ccId);
        kafkaEventData.setService(service);
       // kafkaEventData.setData(createAssociationDataObject(primaryEntityId,source, target));
        log.debug("event for association - {}", kafkaEventData);
        kafkaSender.sendMessage(kafkaEventData);
    }

   private List<AssociationKafkaEventData> createAssociationDataObject(Integer primaryEntityId, AssignUnAssignRecord record, String source, String target) {
        List<AssociationKafkaEventData> eventDataList = new ArrayList<>();

        if(!CollectionUtils.isEmpty(record.getAssign())) {
            record.getAssign().forEach(id -> {
                AssociationKafkaEventData eventData = new AssociationKafkaEventData();
                eventData.setSourceEntity(source);
                eventData.setTargetEntity(target);
                eventData.setSourceId(primaryEntityId);
                eventData.setTargetId(id);
                eventData.setAction("ADD");
                eventDataList.add(eventData);
            });
        }

        if (!CollectionUtils.isEmpty(record.getUnassign())) {
            record.getUnassign().forEach(id -> {
                AssociationKafkaEventData eventData = new AssociationKafkaEventData();
                eventData.setSourceEntity(source);
                eventData.setTargetEntity(target);
                eventData.setSourceId(primaryEntityId);
                eventData.setTargetId(id);
                eventData.setAction("REMOVE");
                eventDataList.add(eventData);
            });
        }
        return eventDataList;
    }
}

