package se.femtearenan.shipstorm.services;

import org.springframework.data.repository.CrudRepository;
import se.femtearenan.shipstorm.model.Nation;

public interface NationService extends CrudRepository<Nation, Long> {
}
