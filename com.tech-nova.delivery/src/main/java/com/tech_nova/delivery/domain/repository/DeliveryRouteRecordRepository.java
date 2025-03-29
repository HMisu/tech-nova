// DeliveryRouteRecordRepository.java
package com.tech_nova.delivery.domain.repository;

import com.tech_nova.delivery.domain.model.delivery.DeliveryRouteRecord;
import com.tech_nova.delivery.domain.model.manager.DeliveryManager;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryRouteRecordRepository {
    Optional<DeliveryRouteRecord> findById(UUID id);

    Optional<DeliveryRouteRecord> findByIdAndIsDeletedFalse(UUID id);

    List<DeliveryRouteRecord> findByDeliveryIdAndIsDeletedFalse(UUID deliveryId);

    @Query("""
    SELECT d.deliveryManager 
    FROM DeliveryRouteRecord d
    WHERE d.currentStatus != 'HUB_ARRIVE'
    GROUP BY d.deliveryManager
    ORDER BY COUNT(d.id) ASC
    LIMIT 1
    """)
    Optional<DeliveryManager> findLeastAssignedDeliveryManager();
}
