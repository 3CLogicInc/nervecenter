package com.ccclogic.nerve.services.webastra;

import com.ccclogic.nerve.dto.AssignedCallcenterDto;
import com.ccclogic.nerve.dto.RouteDto;
import com.ccclogic.nerve.entities.webastra.Route;
import com.ccclogic.nerve.repositories.webastra.RouteRepository;
import com.ccclogic.nerve.repositories.webastra.TenantRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomRouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    TenantRouteRepository tenantRouteRepository;
    // Other dependencies and methods

    public RouteDto getCustomRouteById(Integer routeId) {
       Route route = routeRepository.findById((routeId)).orElseThrow(() -> new IllegalArgumentException("No Route found for id: " + routeId));
        List<AssignedCallcenterDto> ccIds = tenantRouteRepository.findByRouteId(routeId);
        RouteDto routeDto = new RouteDto();
        routeDto.setId(route.getId());
        routeDto.setName(route.getName());
        routeDto.setDomainId((route.getDomainId()));
        routeDto.setIsDefault(route.getIsDefault());
        routeDto.setCreatedAt(route.getCreatedAt());
        routeDto.setUpdatedAt(route.getUpdatedAt());
        routeDto.setRouteExceptions(route.getRouteExceptions());
        return routeDto;
    }

}


