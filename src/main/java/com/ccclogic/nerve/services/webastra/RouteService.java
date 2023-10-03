package com.ccclogic.nerve.services.webastra;

import com.ccclogic.nerve.dto.*;
import com.ccclogic.nerve.entities.webastra.Route;
import com.ccclogic.nerve.entities.webastra.RouteExceptions;
import com.ccclogic.nerve.entities.webastra.TenantRoute;
import com.ccclogic.nerve.repositories.webastra.RouteExceptionsRepository;
import com.ccclogic.nerve.repositories.webastra.RouteRepository;
import com.ccclogic.nerve.repositories.webastra.TenantRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.*;

@Service
public class RouteService {

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    RouteExceptionsRepository routeExceptionsRepository;

    @Autowired
    TenantRouteRepository tenantRouteRepository;

    @Transactional
    public Route saveRoute(RouteRequestDto requestDto) {
        Route route = Route.builder()
                .name(requestDto.getName())
                .domainId(requestDto.getDomainId())
                .isDefault(requestDto.getIsDefault())
                .createdBy(1)
                .updatedBy(1)
                .build();

        if (route.getIsDefault() != null && route.getIsDefault()) {
            routeRepository.updateDefaultRoutes();
        }

        route = routeRepository.save(route);

        saveRouteExceptions(requestDto.getExceptions(), route.getId());

        return routeRepository.findById(route.getId()).get();
    }

    @Transactional
    public void saveRouteExceptions(List<RouteExceptionsDto> exceptions, Integer routeId) {
        routeExceptionsRepository.removeAllByRouteId(routeId);
        List<RouteExceptions> routeExceptions = new ArrayList<>();
        for (RouteExceptionsDto routeExceptionsDto : exceptions) {
            RouteExceptions routeException = RouteExceptions.builder()
                    .routeId(routeId)
                    .exceptionDomainId(routeExceptionsDto.getExceptionDomainId() == -1? null : routeExceptionsDto.getExceptionDomainId())
                    .country(routeExceptionsDto.getCountry())
                    .prefix(routeExceptionsDto.getPrefix())
                    .createdBy(1)
                    .build();
            routeExceptions.add(routeException);
        }
        routeExceptionsRepository.saveAll(routeExceptions);
    }


    @Transactional
    public Route updateRoute(RouteRequestDto requestDto, Integer routeId) {

        Route existingRoute = routeRepository.findById(routeId).orElseThrow(() -> new RuntimeException("Route not found"));

        Route route = Route.builder()
                .id(routeId)
                .name(requestDto.getName())
                .domainId(requestDto.getDomainId())
                .isDefault(requestDto.getIsDefault())
                .updatedBy(1)
                .build();

        if (existingRoute.getIsDefault() != null && existingRoute.getIsDefault() && route.getIsDefault() != null && !route.getIsDefault()) {
            throw new RuntimeException("Cannot remove default route");
        }

        if (route.getIsDefault() != null && route.getIsDefault()) {
            routeRepository.updateDefaultRoutes();
        }

        routeRepository.save(route);

        saveRouteExceptions(requestDto.getExceptions(), route.getId());

        return routeRepository.findById(route.getId()).get();

    }


    @Transactional
    public void deleteRoute(Integer routeId) {
        routeRepository.deleteById(routeId);
        saveRouteExceptions(Arrays.asList(), routeId);
    }


    public List<RouteCallcenterDto> getRoutes() {
        List<Route> routes =  routeRepository.findAll();

        List<RouteCallcenterDto> routeCallcenterDtoList = new ArrayList<>();

        for(Route r : routes){
            RouteCallcenterDto routeCallcenterDto = new RouteCallcenterDto();
            List<AssignedCallcenterInterface> callcenters = tenantRouteRepository.findAllCallcenterByRouteId(r.getId());
            routeCallcenterDto.setId(r.getId());
            routeCallcenterDto.setName(r.getName());
            routeCallcenterDto.setDomainId(r.getDomainId());
            routeCallcenterDto.setIsDefault(r.getIsDefault());
            routeCallcenterDto.setCreatedAt(r.getCreatedAt());
            routeCallcenterDto.setUpdatedAt(r.getUpdatedAt());
            routeCallcenterDto.setDomain(r.getDomain());
            routeCallcenterDto.setRouteExceptions(r.getRouteExceptions());
            routeCallcenterDto.setAssignedCallcenters(callcenters);

            routeCallcenterDtoList.add(routeCallcenterDto);
        }
        return routeCallcenterDtoList;
    }

    public RouteCallcenterDto getRouteById(Integer routeId) {
        Route route =  routeRepository.findById(routeId).orElseThrow(() -> new IllegalArgumentException("Route not found"));

        RouteCallcenterDto routeCallcenterDto = new RouteCallcenterDto();
        List<AssignedCallcenterInterface> callcenters = tenantRouteRepository.findAllCallcenterByRouteId(route.getId());
        routeCallcenterDto.setId(route.getId());
        routeCallcenterDto.setName(route.getName());
        routeCallcenterDto.setDomainId(route.getDomainId());
        routeCallcenterDto.setIsDefault(route.getIsDefault());
        routeCallcenterDto.setCreatedAt(route.getCreatedAt());
        routeCallcenterDto.setUpdatedAt(route.getUpdatedAt());
        routeCallcenterDto.setDomain(route.getDomain());
        routeCallcenterDto.setRouteExceptions(route.getRouteExceptions());
        routeCallcenterDto.setAssignedCallcenters(callcenters);

        return  routeCallcenterDto;
    }

    @Transactional
    public Route setDefaultRoute(Integer routeId) {
        Route route = routeRepository.findById(routeId).orElseThrow(() -> new IllegalArgumentException("Route not found"));
        routeRepository.updateDefaultRoutes();
        route.setIsDefault(true);
        return routeRepository.save(route);
    }

    @Transactional
    public void deleteRouteExceptions(Integer routeId, Integer exceptionId) {
        routeExceptionsRepository.removeByRouteIdAndId(routeId, exceptionId);
    }

    public List<IdNamePair> getAssignedTenantRoutes(Integer tenantId) {
        return tenantRouteRepository.getAssignedTenantRoutes(tenantId);
    }

    public List<IdNamePair> getUnAssignedTenantRoutes(Integer tenantId) {
        return tenantRouteRepository.getUnAssignedTenantRoutes(tenantId);
    }

    @Transactional
    public void assignRoutesToTenant(Integer tenantId, AssignUnAssignRecord assignRecord) {
        tenantRouteRepository.unAssignRoutesFromTenant(tenantId, assignRecord.getUnassign());

        List<TenantRoute> tenantRoutes = new ArrayList<>();

        for (Integer routeId : assignRecord.getAssign()) {
            TenantRoute tenantRoute = TenantRoute.builder()
                    .callcenterId(tenantId)
                    .routeId(routeId)
                    .createdBy(1)
                    .build();
            tenantRoutes.add(tenantRoute);
        }

        tenantRouteRepository.saveAll(tenantRoutes);
    }

    public List<TenantRouteDto> getTenantRoutes() {
        List<TenantRoute> tenantRoutes = tenantRouteRepository.findAll();
        return tenantRoutes.stream().collect(groupingBy(TenantRoute::getCallcenter, toSet())).entrySet().stream().map(es -> {
            TenantRouteDto tenantRouteDto = new TenantRouteDto();
            tenantRouteDto.setCallcenter(es.getKey());
            tenantRouteDto.setAssignedRoutes(es.getValue().stream().map(tr -> new IdNamePair(tr.getRoute().getId(), tr.getRoute().getName())).collect(toList()));
            return tenantRouteDto;
        }).collect(toList());
    }
}

