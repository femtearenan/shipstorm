package se.femtearenan.shipstorm.repositories;

import org.springframework.data.repository.CrudRepository;
import se.femtearenan.shipstorm.enumerations.ShipTypes;
import se.femtearenan.shipstorm.model.ShipClass;

import java.util.List;

public interface ShipClassRepository extends CrudRepository<ShipClass, Long> {
    List<ShipClass> findByName(String name);
    List<ShipClass> findByNameContaining(String name);
    List<ShipClass> findByType(ShipTypes shipTypes);
}
