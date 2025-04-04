package com.tech_nova.delivery.application.service;

import com.tech_nova.delivery.application.dto.RouteEstimateData;

public interface DirectionsApiService {
    RouteEstimateData getRouteEstimateData(String start, String goal);

    RouteEstimateData getRouteEstimateData(String start, String goal, String waypoints);
}