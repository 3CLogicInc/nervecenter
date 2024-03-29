package com.ccclogic.nerve.services.webastra;

import com.ccclogic.nerve.dto.BulkOperationDto;
import com.ccclogic.nerve.entities.webastra.EntryPointHistory;
import com.ccclogic.nerve.dto.FlowEntryPointDto;
import com.ccclogic.nerve.entities.webastra.Entrypoint;

import java.util.List;

public interface EntrypointService {
    List<Entrypoint> getEntrypoints(Integer ccId, String status,String filterValue);

    Entrypoint getEntrypointById(Integer entrypointId);

    Entrypoint save(Entrypoint entryPoint);

    Entrypoint saveRemote(Entrypoint entryPoint);

    Entrypoint update(Integer entrypointId, Entrypoint entryPoint);

    Entrypoint unassign(Integer entrypointId);

    void assignToCallcenter(BulkOperationDto bulkOperationDto);

    void unassignFromCallcenter(BulkOperationDto bulkOperationDto);

    void assignToCallcenterAndFlow(BulkOperationDto bulkOperationDto, String flowName);

    void unassignFlow(BulkOperationDto bulkOperationDto);

    Entrypoint updateEntrypointFlow(Integer entrypointId, Entrypoint entryPoint);

    List<Entrypoint> cancel(BulkOperationDto bulkOperationDto);

    List<Entrypoint> cancelRemote(BulkOperationDto bulkOperationDto);

    void removeEntrypointFlow(Integer ccId, Integer flowId);

    List<EntryPointHistory> getEntryPointHistory(Integer entrypointId);

    List<Entrypoint> getAssignedEntryPoints(Integer flowId, Integer ccId);

    List<Entrypoint> getUnassignedEntryPoints(Integer flowId, Integer ccId);

    void assignUnassign(Integer flowId, Integer ccId, String flowName, FlowEntryPointDto flowEntryPointDto);

    List<Entrypoint> getAllEntryPointsByIds(List<Integer> entryPointIds);

    List<Entrypoint> getAllEntryPointsByFlowId(Integer flowId);
}
