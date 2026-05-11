package org.app.shipment.repository;

import org.app.shipment.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    List<Shipment> findByClientId(Long clientId);

    Optional<Shipment> findByIdAndClientId(
            Long shipmentId,
            Long clientId
    );
}
