package se.femtearenan.shipstorm.services;

import org.springframework.data.repository.CrudRepository;
import se.femtearenan.shipstorm.model.Ship;

public interface ShipService extends CrudRepository<Ship, Long> {
}
