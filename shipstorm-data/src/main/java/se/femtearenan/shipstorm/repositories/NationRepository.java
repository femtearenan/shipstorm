package se.femtearenan.shipstorm.repositories;

import org.springframework.data.repository.CrudRepository;
import se.femtearenan.shipstorm.model.Nation;

public interface NationRepository extends CrudRepository<Nation, Long> {
}
