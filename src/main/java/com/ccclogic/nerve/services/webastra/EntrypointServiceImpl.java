package com.ccclogic.nerve.services.webastra;

import com.ccclogic.nerve.dto.BulkOperationDto;
import com.ccclogic.nerve.entities.webastra.EntryPointHistory;
import com.ccclogic.nerve.dto.FlowEntryPointDto;
import com.ccclogic.nerve.entities.webastra.Entrypoint;
import com.ccclogic.nerve.entities.webastra.enums.EntrypointStatus;
import com.ccclogic.nerve.repositories.webastra.EntryPointHistoryRepository;
import com.ccclogic.nerve.repositories.webastra.EntrypointRepository;
import com.ccclogic.nerve.util.ObjectUtil;
import com.ccclogic.nerve.util.SecurityUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntrypointServiceImpl implements EntrypointService {

    @Autowired
    EntrypointRepository entrypointRepository;

    @Autowired
    EntityManager webastraEntityManager;

    @Autowired
    EntryPointHistoryRepository entryPointHistoryRepository;


    @Override
    public List<Entrypoint> getEntrypoints(Integer ccId, String status, String filter_value) {
        // Create a base query
        String query = "SELECT e FROM Entrypoint e WHERE 1=1";

        // Add conditions based on the parameters
        if (ccId != null) {
            query += " AND e.ccId = :ccId";
        }

        if (status != null && !status.isEmpty()) {
            query += " AND e.status = :status";
        }

        // Create a query object
        Query jpqlQuery = webastraEntityManager.createQuery(query, Entrypoint.class);

        // Set parameter values if applicable
        if (ccId != null) {
            jpqlQuery.setParameter("ccId", ccId);
        }

        if (status != null && !status.isEmpty()) {
            jpqlQuery.setParameter("status", status);
        }

        // Execute the query
        List<Entrypoint> entrypoints = jpqlQuery.getResultList();

        // Filter results by filterValue (if provided)
        if (filter_value != null && !filter_value.isEmpty()) {
            if (status != null && !status.isEmpty()) {
                // Both status and filterValue provided
                List<Entrypoint> filteredByStatusAndKeyword = entrypointRepository.searchByKeywordAndStatus("%" + filter_value + "%", status);
                if (!filteredByStatusAndKeyword.isEmpty()) {
                    return filteredByStatusAndKeyword;
                }
            } else {
                // Only filterValue provided
                List<Entrypoint> filteredByKeyword = entrypointRepository.searchByKeyword("%" + filter_value + "%");
                if (!filteredByKeyword.isEmpty()) {
                    return filteredByKeyword;
                }
            }
        }

        return entrypoints;

    }

    @Override
    public List<Entrypoint> getAllEntryPointsByFlowId(Integer flowId) {
        List<Entrypoint> entrypointList = entrypointRepository.findAllEntryPointsByFlowId(flowId);
        return entrypointList;
    }

    @Override
    public Entrypoint getEntrypointById(Integer entrypointId) {
        return entrypointRepository.findById(entrypointId).orElseThrow(() -> new IllegalArgumentException("Entrypoint not found"));
    }

    @Override
    public List<Entrypoint> getAllEntryPointsByIds(List<Integer> entryPointIds) {
        List<Entrypoint> entrypointList = entrypointRepository.findAllByIdIn(entryPointIds);
        return entrypointList;
    }

    @Override
    public Entrypoint save(Entrypoint entryPoint) {
        entryPoint.setStatus("AVAILABLE");

        if (entryPoint.getFlowId() != null) {
            entryPoint.setStatus("ACTIVE");
        }
        entryPoint.setUpdatedById(SecurityUtil.getLoggedInUser().getEntityId());
        entryPoint.setCreatedById(SecurityUtil.getLoggedInUser().getEntityId());
        entryPoint.setCreatedOn("OPS");
        Entrypoint savedEntrypoint = entrypointRepository.save(entryPoint);
        return savedEntrypoint;
    }

    @Override
    public Entrypoint saveRemote(Entrypoint entryPoint) {
        if(entrypointRepository.existsByEntrypointAndChannel(entryPoint.getEntrypoint(), entryPoint.getChannel())){
            return entryPoint;
        }
        entryPoint.setStatus("AVAILABLE");

        if (entryPoint.getFlowId() != null) {
            entryPoint.setStatus("ACTIVE");
        }
        entryPoint.setUpdatedById(SecurityUtil.getLoggedInUser().getEntityId());
        entryPoint.setCreatedById(SecurityUtil.getLoggedInUser().getEntityId());
        entryPoint.setCreatedOn("TENANT");
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
    public void assignToCallcenter(BulkOperationDto bulkOperationDto) {
        Integer ccId = (Integer) bulkOperationDto.getMappedId().stream().findFirst().orElseThrow(() -> new IllegalArgumentException("Callcenter Id not provided")).get("id");
        List<Entrypoint> entrypoints = entrypointRepository.findAllById(bulkOperationDto.getSelectedListRecords());
        if (entrypoints.isEmpty()) {
            throw new IllegalArgumentException("Invalid entrypoint Ids provided");
        }

        List<Entrypoint> alreadyAssignedEntrypoints = entrypoints.stream().filter(e -> e.getCcId() != null).collect(Collectors.toList());
        if (!alreadyAssignedEntrypoints.isEmpty()) {
            throw new IllegalArgumentException("Some of  the entrypoints are already assigned to callcenters");
        }

        List<Entrypoint> cancelledEntrypoints =  entrypoints.stream().filter(e -> e.getStatus().equals(EntrypointStatus.CANCELLED.name())).collect(Collectors.toList());
        if(!cancelledEntrypoints.isEmpty()){
            throw new IllegalArgumentException("Some of  the entrypoints are cancelled and hence cannot be assigned to callcenter.");
        }


        entrypoints = entrypoints.stream().map(e -> {
            e.setCcId(ccId);
            return e;
        }).collect(Collectors.toList());

        entrypointRepository.saveAll(entrypoints);
    }

    @Override
    public void unassignFromCallcenter(BulkOperationDto bulkOperationDto) {
        List<Entrypoint> entrypoints = entrypointRepository.findAllById(bulkOperationDto.getSelectedListRecords());
        if (entrypoints.isEmpty()) {
            throw new IllegalArgumentException("Invalid entrypoint Ids provided");
        }

        List<Entrypoint> cancelledEntrypoints =  entrypoints.stream().filter(e -> e.getStatus().equals(EntrypointStatus.CANCELLED.name())).collect(Collectors.toList());
        if(!cancelledEntrypoints.isEmpty()){
            throw new IllegalArgumentException("Some of  the entrypoints are cancelled and hence cannot be unassigned to callcenter.");
        }

        entrypoints = entrypoints.stream().map(e -> {
            e.setCcId(null);
            return e;
        }).collect(Collectors.toList());

        entrypointRepository.saveAll(entrypoints);
    }

    @Override
    public void assignToCallcenterAndFlow(BulkOperationDto bulkOperationDto, String flowName) {
        Integer flowId = (Integer) bulkOperationDto.getMappedId().stream().findFirst().orElseThrow(() -> new IllegalArgumentException("FlowId Id not provided")).get("id");
        Integer ccId = (Integer) bulkOperationDto.getMappedId().stream().skip(1).findFirst().orElseThrow(() -> new IllegalArgumentException("Callcenter Id not provided")).get("ccId");
        List<Entrypoint> entrypoints = entrypointRepository.findAllById(bulkOperationDto.getSelectedListRecords());
        if (entrypoints.isEmpty()) {
            throw new IllegalArgumentException("Invalid entrypoint Ids provided");
        }

        List<Entrypoint> alreadyAssignedEntrypointsByCCId = entrypoints.stream().filter(e -> e.getCcId() != null && !e.getCcId().equals(ccId)).collect(Collectors.toList());
        if (!alreadyAssignedEntrypointsByCCId.isEmpty()) {
            throw new IllegalArgumentException("Some of  the entrypoints are already assigned to some other callcenter");
        }

        List<Entrypoint> alreadyAssignedEntrypointsByFlowId = entrypoints.stream().filter(e -> e.getFlowId() != null && !e.getFlowId().equals(flowId)).collect(Collectors.toList());
        if (!alreadyAssignedEntrypointsByFlowId.isEmpty()) {
            throw new IllegalArgumentException("Some of  the entrypoints are already assigned to some other flow");
        }


        entrypoints = entrypoints.stream().map(e -> {
            e.setCcId(ccId);
            e.setFlowId(flowId);
            e.setFlowName(flowName);
            e.setStatus("ACTIVE");
            return e;
        }).collect(Collectors.toList());

        entrypointRepository.saveAll(entrypoints);
    }

    @Override
    public void unassignFlow(BulkOperationDto bulkOperationDto) {
        List<Entrypoint> entrypoints = entrypointRepository.findAllById(bulkOperationDto.getSelectedListRecords());
        if (entrypoints.isEmpty()) {
            throw new IllegalArgumentException("Invalid entrypoint Ids provided");
        }

        entrypoints = entrypoints.stream().map(e -> {
            e.setFlowId(null);
            e.setFlowName(null);
            e.setStatus("AVAILABLE");
            return e;
        }).collect(Collectors.toList());

        entrypointRepository.saveAll(entrypoints);
    }

    @Override
    public Entrypoint updateEntrypointFlow(Integer entrypointId, Entrypoint entryPoint) {
        Entrypoint savedEntrypoint = entrypointRepository.findById(entrypointId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Entrypoint Id provided"));

        savedEntrypoint.setUpdatedById(SecurityUtil.getLoggedInUser().getEntityId());
        if (entryPoint.getFlowId() != null && entryPoint.getFlowId() > 0) {
            savedEntrypoint.setFlowId(entryPoint.getFlowId());
            savedEntrypoint.setFlowName(entryPoint.getFlowName());
            savedEntrypoint.setStatus("ACTIVE");
        } else {
            savedEntrypoint.setFlowId(null);
            savedEntrypoint.setFlowName(null);
            savedEntrypoint.setStatus("AVAILABLE");
        }
        return entrypointRepository.save(savedEntrypoint);
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
    public List<Entrypoint> cancel(BulkOperationDto bulkOperationDto) {
        List<Entrypoint> savedEntrypoints = entrypointRepository.findAllById(bulkOperationDto.getSelectedListRecords());
        savedEntrypoints = savedEntrypoints.stream().map(e -> {e.setStatus("CANCELLED"); return e;}).collect(Collectors.toList());
        return entrypointRepository.saveAll(savedEntrypoints);
    }

    @Override
    public List<Entrypoint> cancelRemote(BulkOperationDto bulkOperationDto) {
        Integer ccId = (Integer) bulkOperationDto.getMappedId().stream().findFirst().orElseThrow(() -> new IllegalArgumentException("Callcenter Id not provided")).get("ccId");
        List<Entrypoint> savedEntrypoints = entrypointRepository.findAllById(bulkOperationDto.getSelectedListRecords());
        List<Entrypoint> entrypointCreatedOnOPS = savedEntrypoints.stream().filter(e -> e.getCreatedOn().equals("OPS")).collect(Collectors.toList());
        if(!entrypointCreatedOnOPS.isEmpty()){
            throw new IllegalArgumentException("Some entrypoints are created on OPS");
        }

        List<Entrypoint> entrypointStatusCancelled = savedEntrypoints.stream().filter(e->e.getStatus().equalsIgnoreCase("ACTIVE")).collect(Collectors.toList());
        if(!entrypointStatusCancelled.isEmpty()){
            return entrypointStatusCancelled;
        }

        List<Entrypoint> entrypointAssignedToTenant = savedEntrypoints.stream().filter(e -> e.getCcId()== null || !e.getCcId().equals(ccId)).collect(Collectors.toList());
        if(!entrypointAssignedToTenant.isEmpty()){
            throw new IllegalArgumentException("Some entrypoints are either not assigned or assigned to different tenant. and cannot be cancelled");
        }

        savedEntrypoints = savedEntrypoints.stream().map(e -> {e.setStatus("CANCELLED"); return e;}).collect(Collectors.toList());
        return entrypointRepository.saveAll(savedEntrypoints);
    }

    @Override
    public void removeEntrypointFlow(Integer ccId, Integer flowId) {
        List<Entrypoint> entrypoints = entrypointRepository.findAllByCcIdAndFlowIdAndStatusNot(ccId, flowId, EntrypointStatus.CANCELLED.name());
        entrypoints = entrypoints.stream().map(entrypoint -> {
            entrypoint.setFlowId(null);
            entrypoint.setFlowName(null);
            entrypoint.setStatus(EntrypointStatus.AVAILABLE.name());
            return entrypoint;
        }).collect(Collectors.toList());
        entrypointRepository.saveAll(entrypoints);
    }

    @Override
    public List<EntryPointHistory> getEntryPointHistory(Integer entrypointId) {
        List<EntryPointHistory> entryPointHistoryList = entryPointHistoryRepository.findAllById(entrypointId);
        return entryPointHistoryList;
    }

    public List<Entrypoint> getAssignedEntryPoints(Integer flowId, Integer ccId) {
        List<Entrypoint> assignedList = entrypointRepository.findAllByCcIdAndFlowId(ccId, flowId);
        return assignedList;
    }

    @Override
    public List<Entrypoint> getUnassignedEntryPoints(Integer flowId, Integer ccId) {
        List<Entrypoint> unassignedList = entrypointRepository.findAllByCcIdAndFlowId(ccId, null);
        return unassignedList;
    }

    @Override
    public void assignUnassign(Integer flowId, Integer ccId, String flowName, FlowEntryPointDto flowEntryPointDto) {
        if(flowEntryPointDto.getUnassign() != null && !flowEntryPointDto.getUnassign().isEmpty()){
            unassign(flowEntryPointDto);
        }

        if(flowEntryPointDto.getAssign() != null && !flowEntryPointDto.getAssign().isEmpty()){
            for(Integer id : flowEntryPointDto.getAssign()){
                Entrypoint ep = entrypointRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("No entry point exists for Id : " + id));


                ep.setFlowId(flowId);
                ep.setFlowName(flowName);
                ep.setStatus("ACTIVE");
                ep.setUpdatedById(SecurityUtil.getLoggedInUser().getEntityId().intValue());
                entrypointRepository.save(ep);
            }
        }
    }

    private void unassign (FlowEntryPointDto flowEntryPointDto) {
        for(Integer id : flowEntryPointDto.getUnassign()){
            Entrypoint ep = entrypointRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("No entry point exists for Id : " + id));

            ep.setFlowId(null);
            ep.setFlowName(null);
            ep.setStatus("AVAILABLE");
            ep.setUpdatedById(SecurityUtil.getLoggedInUser().getEntityId().intValue());
            entrypointRepository.save(ep);
        }
    }

}
