package com.ccclogic.nerve.controller;

import com.ccclogic.nerve.dto.FlowEntryPointDto;
import com.ccclogic.nerve.entities.webastra.Entrypoint;
import com.ccclogic.nerve.services.webastra.EntrypointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/flows")
public class FlowEntryPointAssociationController {

    @Autowired
    EntrypointService entrypointService;

    @GetMapping("/{flowId}/entrypoints/assigned")
    public List<Entrypoint>  getAssignedEntryPoints(@PathVariable Integer flowId, @RequestParam(value = "ccId", required = false) Integer ccId){
        return entrypointService.getAssignedEntryPoints(flowId,ccId);
    }

    @GetMapping("/{flowId}/entrypoints/unassigned")
    public List<Entrypoint> getUnassignedEntryPoints(@PathVariable Integer flowId, @RequestParam(value = "ccId", required = false) Integer ccId){
        return entrypointService.getUnassignedEntryPoints(flowId,ccId);
    }

    @PostMapping("/{flowId}/entrypoints/assign")
    public void assignUnassign(@PathVariable Integer flowId, @RequestParam(value = "ccId", required = false) Integer ccId, @RequestParam String flowName, @RequestBody FlowEntryPointDto flowEntryPointDto){
        entrypointService.assignUnassign(flowId, ccId, flowName, flowEntryPointDto);
    }
}
