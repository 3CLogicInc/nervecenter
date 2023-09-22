package com.ccclogic.nerve.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;



//@Configuration
public class KafkaConfig {

//    @Bean
   NewTopic ctiServiceTopic(@Value("${default.integration.kafka.cti.service}")String ctiService){
       return new NewTopic(ctiService, 3, (short) 3);
   }



}
