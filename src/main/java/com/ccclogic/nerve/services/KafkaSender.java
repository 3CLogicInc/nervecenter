package com.ccclogic.nerve.services;

import com.ccclogic.nerve.dto.KafkaEventData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Service
public class KafkaSender {

    @Value(value = "${spring.kafka.properties.topic.key}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, KafkaEventData> kafkaTemplate;

//    @PostConstruct
//    public void init() {
////        kafkaTemplate.send("dev_portal_ipc", "init");
//    }

    public void sendMessage(KafkaEventData eventData) {
        kafkaTemplate.send(topic, eventData);
    }

}

