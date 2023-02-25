package com.ccclogic.nerve.controller;


import com.ccclogic.nerve.entities.webastra.Entrypoint;
import com.ccclogic.nerve.services.webastra.EntrypointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/entrypoints")
public class EntrypointController {

    @Autowired
    EntrypointService entrypointService;

    @GetMapping
    public List<Entrypoint> getAllEntrypointsByStatus(@RequestParam(value = "ccId", required = false) Integer ccId,
                                                      @RequestParam(value = "status", required = false) String status) {
        return entrypointService.getEntrypoints(ccId, status);
    }

    @PostMapping
    public Entrypoint saveEntryPoint(@RequestBody Entrypoint entryPoint) {
        return entrypointService.save(entryPoint);
    }

    @PostMapping("/{entrypointId}")
    public Entrypoint updateEntryPoint(@PathVariable Integer entrypointId, @RequestBody Entrypoint entryPoint) {
        return entrypointService.update(entrypointId, entryPoint);
    }

    @DeleteMapping("/{entrypointId}/unassign")
    public Entrypoint unassignEntryPoint(@PathVariable Integer entrypointId) {
        return entrypointService.unassign(entrypointId);
    }

    @DeleteMapping("/{entrypointId}/cancel")
    public Entrypoint cancelEntryPoint(@PathVariable Integer entrypointId) {
        return entrypointService.cancel(entrypointId);
    }


}
