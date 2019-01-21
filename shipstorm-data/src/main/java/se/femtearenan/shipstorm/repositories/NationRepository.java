package se.femtearenan.shipstorm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.femtearenan.shipstorm.enumerations.ShipType;
import se.femtearenan.shipstorm.model.Nation;
import se.femtearenan.shipstorm.model.ShipClass;

import java.util.List;

public interface NationRepository extends JpaRepository<Nation, Long> {
    List<Nation> findByName(String name);
    List<Nation> findByNameContaining(String name);
    List<Nation> findByShipsShipClassShipType(ShipType shipType);
    List<Nation> findByShipsNameContaining(String name);
    List<Nation> findByShipsPennantContaining(String pennant);
    List<Nation> findByShipsShipClass(ShipClass shipClass);
    List<Nation> findByShipsSensorsNameContaining(String sensor);
}
