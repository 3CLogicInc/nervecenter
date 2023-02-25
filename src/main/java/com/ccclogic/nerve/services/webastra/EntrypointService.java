package com.ccclogic.nerve.services.webastra;

import com.ccclogic.nerve.entities.webastra.Entrypoint;

import java.util.List;

public interface EntrypointService {
    List<Entrypoint> getEntrypoints(Integer ccId, String status);

    Entrypoint save(Entrypoint entryPoint);

    Entrypoint update(Integer entrypointId, Entrypoint entryPoint);

    Entrypoint unassign(Integer entrypointId);

    Entrypoint cancel(Integer entrypointId);

}
