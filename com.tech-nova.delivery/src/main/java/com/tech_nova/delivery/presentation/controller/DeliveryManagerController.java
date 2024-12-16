package com.tech_nova.delivery.presentation.controller;

import com.tech_nova.delivery.application.dto.res.DeliveryManagerResponse;
import com.tech_nova.delivery.application.service.DeliveryManagerService;
import com.tech_nova.delivery.presentation.dto.ApiResponseDto;
import com.tech_nova.delivery.presentation.request.DeliveryManagerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/delivery-managers")
@RequiredArgsConstructor
public class DeliveryManagerController {
    private final DeliveryManagerService deliveryManagerService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<UUID>> createDeliveryManager(
            @RequestBody DeliveryManagerRequest request,
            @RequestHeader(value = "X-User-Id", required = true) UUID userId,
            @RequestHeader(value = "X-Role", required = true) String role
    ) {
        UUID deliveryManagerId = deliveryManagerService.createDeliveryManager(request.toDTO(), userId, role);
        return ResponseEntity.ok(ApiResponseDto.success("Delivery Manager created successfully", deliveryManagerId));
    }

    @GetMapping("/{delivery_manager_id}")
    public ResponseEntity<ApiResponseDto<DeliveryManagerResponse>> createDeliveryManager(
            @PathVariable("delivery_manager_id") UUID deliveryManagerId,
            @RequestHeader(value = "X-User-Id", required = true) UUID userId,
            @RequestHeader(value = "X-Role", required = true) String role
    ) {
        DeliveryManagerResponse deliveryManager = deliveryManagerService.getDeliveryManager(deliveryManagerId, userId, role);
        return ResponseEntity.ok(ApiResponseDto.success("Delivery Manager created successfully", deliveryManager));
    }
}
