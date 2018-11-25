package se.femtearenan.shipstorm.repositories;

import org.springframework.data.repository.CrudRepository;
import se.femtearenan.shipstorm.model.ShipClass;

public interface ShipClassRepository extends CrudRepository<ShipClass, Long> {
}
