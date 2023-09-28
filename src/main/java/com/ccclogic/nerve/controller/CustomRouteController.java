package com.ccclogic.nerve.controller;

import com.ccclogic.nerve.dto.RouteDto;
import com.ccclogic.nerve.services.webastra.CustomRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/custom-route")
public class CustomRouteController {

    @Autowired
    private CustomRouteService customRouteService;

    @GetMapping("/{routeId}")
    public RouteDto getCustomRoute(@PathVariable Integer routeId) {
        return customRouteService.getCustomRouteById(routeId);
    }
}
