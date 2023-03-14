package com.ccclogic.nerve.controller;


import com.ccclogic.nerve.dto.BulkOperationDto;
import com.ccclogic.nerve.entities.webastra.EntryPointHistory;
import com.ccclogic.nerve.entities.webastra.Entrypoint;
import com.ccclogic.nerve.services.webastra.EntrypointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/entrypoints")
public class EntrypointController {

    @Autowired
    EntrypointService entrypointService;

    @GetMapping
    public List<Entrypoint> getAllEntrypoints(@RequestParam(value = "ccId", required = false) Integer ccId,
                                              @RequestParam(value = "status", required = false) String status) {
        return entrypointService.getEntrypoints(ccId, status);
    }

    @GetMapping("/{entrypointId}")
    public Entrypoint getEntrypointById(@PathVariable Integer entrypointId) {
        return entrypointService.getEntrypointById(entrypointId);
    }

    @PostMapping("/remote")
    public Entrypoint saveEntryPoint(@RequestBody Entrypoint entryPoint) {
        return entrypointService.saveRemote(entryPoint);
    }

    @PostMapping
    public Entrypoint saveEntryPointFromNerve(@RequestBody Entrypoint entryPoint) {
        return entrypointService.save(entryPoint);
    }

    @PutMapping("/{entrypointId}")
    public Entrypoint updateEntryPoint(@PathVariable Integer entrypointId, @RequestBody Entrypoint entryPoint) {
        return entrypointService.update(entrypointId, entryPoint);
    }

    @PutMapping("/{entrypointId}/flow")
    public Entrypoint updateEntryPointFlow(@PathVariable Integer entrypointId, @RequestBody Entrypoint entryPoint) {
        return entrypointService.updateEntrypointFlow(entrypointId, entryPoint);
    }

    @DeleteMapping("/flow/remove")
    public ResponseEntity removeEntryPointFlow(@RequestParam Integer flowId, @RequestParam Integer ccId) {
        entrypointService.removeEntrypointFlow(ccId, flowId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{entrypointId}/unassign")
    public Entrypoint unassignEntryPoint(@PathVariable Integer entrypointId) {
        return entrypointService.unassign(entrypointId);
    }

    @PutMapping("/bulk/assign/callcenters")
    public void assignCallcenter(@RequestBody BulkOperationDto bulkOperationDto) {
        entrypointService.assignToCallcenter(bulkOperationDto);
    }

    @PutMapping("/bulk/unassign/callcenters")
    public void unassignCallcenter(@RequestBody BulkOperationDto bulkOperationDto) {
        entrypointService.unassignFromCallcenter(bulkOperationDto);
    }

    @PutMapping("/bulk/assign/flow")
    public void assignFlow(@RequestBody BulkOperationDto bulkOperationDto) {
        entrypointService.assignToCallcenterAndFlow(bulkOperationDto);
    }

    @PutMapping("/bulk/unassign/flow")
    public void unassignFlowAndCallcenter(@RequestBody BulkOperationDto bulkOperationDto) {
        entrypointService.unassignFlow(bulkOperationDto);
    }

    @DeleteMapping("/bulk/cancel/remote")
    public void bulkCancelEntrypointFromRemote(@RequestBody BulkOperationDto bulkOperationDto) {
        entrypointService.unassignFlow(bulkOperationDto);
    }

    @DeleteMapping("/bulk/cancel/nerve")
    public void bulkCancelEntrypointFromNerve(@RequestBody BulkOperationDto bulkOperationDto) {
        entrypointService.unassignFlow(bulkOperationDto);
    }

    @DeleteMapping("/bulk/cancel")
    public List<Entrypoint> cancelEntryPoint(@RequestBody BulkOperationDto bulkOperationDto) {
        return entrypointService.cancel(bulkOperationDto);
    }

    @DeleteMapping("/bulk/remote/cancel")
    public List<Entrypoint> cancelEntryPointRemote(@RequestBody BulkOperationDto bulkOperationDto) {
        return entrypointService.cancelRemote(bulkOperationDto);
    }

    @GetMapping("/{entrypoint}/history")
    public List<EntryPointHistory> getEntryPointHistory(@PathVariable String entrypoint){
        return entrypointService.getEntryPointHistory(entrypoint);
    }


}
