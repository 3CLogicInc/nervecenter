package com.ccclogic.nerve.controller;

import com.ccclogic.nerve.dto.AssignUnAssignRecord;
import com.ccclogic.nerve.dto.RouteRequestDto;
import com.ccclogic.nerve.entities.webastra.Route;
import com.ccclogic.nerve.services.kafka.KafkaEventProducer;
import com.ccclogic.nerve.services.webastra.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/routes")
public class RouteController {

    @Autowired
    RouteService routeService;

    @Autowired
    KafkaEventProducer kafkaEventProducer;

    @PostMapping
    public Route saveRoute(@RequestBody RouteRequestDto routeRequestDto) {
        return routeService.saveRoute(routeRequestDto);
    }

    @PatchMapping("/{routeId}")
    public Route updateRoute(@PathVariable Integer routeId, @RequestBody RouteRequestDto routeRequestDto) {
        return routeService.updateRoute(routeRequestDto, routeId);
    }

    @DeleteMapping("/{routeId}")
    public void deleteRoute(@PathVariable Integer routeId) {
        routeService.deleteRoute(routeId);
    }

    @GetMapping("/{routeId}")
    public Route getRoute(@PathVariable Integer routeId) {
        return routeService.getRouteById(routeId);
    }

    @GetMapping
    public List<Route> getRoutes() {
        return routeService.getRoutes();
    }

    @PatchMapping("/{routeId}/default")
    public Route setDefaultRoute(@PathVariable Integer routeId) {
        return routeService.setDefaultRoute(routeId);
    }

    @DeleteMapping("/{routeId}/exceptions/{exceptionId}")
    public void deleteRouteException(@PathVariable Integer routeId, @PathVariable Integer exceptionId) {
        routeService.deleteRouteExceptions(routeId, exceptionId);
    }

    @PostMapping("/postEvent")
    public void postEvent(@RequestBody RouteRequestDto routeRequestDto) {
        Integer primaryEntityId = routeRequestDto.getPrimaryEntityId();
        AssignUnAssignRecord record = routeRequestDto.getAssignUnAssignRecord();

        kafkaEventProducer.postAeEvent(primaryEntityId, record,"ROUTES","CALLCENTER");
    }

}
