package se.femtearenan.shipstorm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.femtearenan.shipstorm.enumerations.ShipType;
import se.femtearenan.shipstorm.model.ShipClass;

import java.util.List;

public interface ShipClassRepository extends JpaRepository<ShipClass, Long> {
    List<ShipClass> findByName(String name);
    List<ShipClass> findByNameContaining(String name);
    List<ShipClass> findByShipType(ShipType shipType);
}
