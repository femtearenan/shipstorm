package se.femtearenan.shipstorm.services;

import org.springframework.data.repository.CrudRepository;
import se.femtearenan.shipstorm.model.ShipClass;

public interface ShipClassService extends CrudRepository<ShipClass, Long> {
}
