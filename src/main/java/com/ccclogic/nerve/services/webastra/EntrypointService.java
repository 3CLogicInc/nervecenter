package com.ccclogic.nerve.services.webastra;

import com.ccclogic.nerve.dto.BulkOperationDto;
import com.ccclogic.nerve.entities.webastra.Entrypoint;

import java.util.List;

public interface EntrypointService {
    List<Entrypoint> getEntrypoints(Integer ccId, String status);

    Entrypoint getEntrypointById(Integer entrypointId);

    Entrypoint save(Entrypoint entryPoint);

    Entrypoint saveRemote(Entrypoint entryPoint);

    Entrypoint update(Integer entrypointId, Entrypoint entryPoint);

    Entrypoint unassign(Integer entrypointId);

    void assignToCallcenter(BulkOperationDto bulkOperationDto);

    void unassignFromCallcenter(BulkOperationDto bulkOperationDto);

    void assignToCallcenterAndFlow(BulkOperationDto bulkOperationDto);

    void unassignFlow(BulkOperationDto bulkOperationDto);

    Entrypoint updateEntrypointFlow(Integer entrypointId, Entrypoint entryPoint);

    List<Entrypoint> cancel(BulkOperationDto bulkOperationDto);

    List<Entrypoint> cancelRemote(BulkOperationDto bulkOperationDto);

    void removeEntrypointFlow(Integer ccId, Integer flowId);
}
