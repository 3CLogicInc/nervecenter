package com.ccclogic.nerve.entities.common;

import com.ccclogic.nerve.util.PrePersistUtil;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Component
public class DefaultFieldEventListener {

    @PrePersist
    public void onPrePersist(final Object obj) {
        PrePersistUtil.prePersist(obj);
    }

    @PreUpdate
    public void onPreUpdate(final Object obj) {
        PrePersistUtil.preUpdate(obj);
    }
}
