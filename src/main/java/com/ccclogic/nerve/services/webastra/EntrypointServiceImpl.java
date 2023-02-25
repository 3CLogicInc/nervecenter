package com.ccclogic.nerve.services.webastra;

import com.ccclogic.nerve.entities.webastra.Entrypoint;
import com.ccclogic.nerve.repositories.webastra.EntrypointRepository;
import com.ccclogic.nerve.util.ObjectUtil;
import com.ccclogic.nerve.util.SecurityUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class EntrypointServiceImpl implements EntrypointService {

    @Autowired
    EntrypointRepository entrypointRepository;

    @Autowired
    EntityManager webastraEntityManager;


    @Override
    public List<Entrypoint> getEntrypoints(Integer ccId, String status) {
        String query = "select ep from Entrypoint ep where 1=1";
        if (ccId != null) {
            query += " AND ccid = " + ccId;
        }

        if (!StringUtils.isBlank(status)) {
            query += " AND status IN (" + status + ")";
        }

        return webastraEntityManager.createQuery(query).getResultList();
    }

    @Override
    public Entrypoint save(Entrypoint entryPoint) {
        entryPoint.setStatus("INACTIVE");
        entryPoint.setUpdatedById(SecurityUtil.getLoggedInUser().getEntityId());
        entryPoint.setCreatedById(SecurityUtil.getLoggedInUser().getEntityId());
        Entrypoint savedEntrypoint = entrypointRepository.save(entryPoint);
        return savedEntrypoint;
    }

    @SneakyThrows
    @Override
    public Entrypoint update(Integer entrypointId, Entrypoint entryPoint) {
        Entrypoint savedEntrypoint = entrypointRepository.findById(entrypointId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Entrypoint Id provided"));

        entryPoint = ObjectUtil.mergeObjects(savedEntrypoint, entryPoint, true);
        entryPoint.setUpdatedById(SecurityUtil.getLoggedInUser().getEntityId());
        if (entryPoint.getFlowId() != null) {
            entryPoint.setStatus("ACTIVE");
        }
        return entrypointRepository.save(entryPoint);
    }

    @Override
    public Entrypoint unassign(Integer entrypointId) {
        Entrypoint savedEntrypoint = entrypointRepository.findById(entrypointId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Entrypoint Id provided"));

        savedEntrypoint.setCcId(null);
        savedEntrypoint.setFlowId(null);
        savedEntrypoint.setFlowName(null);
        savedEntrypoint.setStatus("AVAILABLE");
        return entrypointRepository.save(savedEntrypoint);
    }

    @Override
    public Entrypoint cancel(Integer entrypointId) {
        Entrypoint savedEntrypoint = entrypointRepository.findById(entrypointId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Entrypoint Id provided"));

        savedEntrypoint.setCcId(null);
        savedEntrypoint.setFlowId(null);
        savedEntrypoint.setFlowName(null);
        savedEntrypoint.setStatus("CANCELED");
        return entrypointRepository.save(savedEntrypoint);
    }
}
