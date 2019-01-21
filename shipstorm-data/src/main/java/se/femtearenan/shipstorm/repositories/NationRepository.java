package se.femtearenan.shipstorm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.femtearenan.shipstorm.enumerations.ShipType;
import se.femtearenan.shipstorm.model.Nation;

import java.util.List;

public interface NationRepository extends JpaRepository<Nation, Long> {
    List<Nation> findByName(String name);
    List<Nation> findByNameContaining(String name);
    List<Nation> findByShipClassesShipType(ShipType shipType);
    List<Nation> findByShipsNameContaining(String name);
    List<Nation> findByShipsPennantContaining(String pennant);
    List<Nation> findByShipClassesNameContaining(String name);
    List<Nation> findByShipsSensorsNameContaining(String sensor);
}
