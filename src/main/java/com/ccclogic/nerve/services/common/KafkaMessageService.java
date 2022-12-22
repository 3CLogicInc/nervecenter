package com.ccclogic.nerve.services.common;

import com.google.common.util.concurrent.ListenableFuture;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageService implements MessageService{



    public static enum RemoteService {
        CTI_SERVICE("default.kafka.message.cti.service"),
        PRESENCE_SERVICE("default.kafka.message.presence.service"),
        ANALYTICS_SERVICE("default.kafka.message.analytics.service"),
        ACTIVITY_SERVICE("default.kafka.message.activity.service");

        String key;

        RemoteService(String key) {
            this.key = key;
        }
    };



    public ListenableFuture send(RemoteService service, String key, String body){
        return  null;
    }

    private static String getTopic(RemoteService service){
        return  null;
    }


}
