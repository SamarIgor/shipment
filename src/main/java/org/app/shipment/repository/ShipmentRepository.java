package org.app.shipment.repository;

import org.app.shipment.model.Shipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    Page<Shipment> findByClientId(Long clientId, Pageable pageable);

    Optional<Shipment> findByIdAndClientId(
            Long shipmentId,
            Long clientId
    );
}
