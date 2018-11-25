package se.femtearenan.shipstorm.repositories;

import org.springframework.data.repository.CrudRepository;
import se.femtearenan.shipstorm.model.Ship;

public interface ShipRepository extends CrudRepository<Ship, Long> {
}
