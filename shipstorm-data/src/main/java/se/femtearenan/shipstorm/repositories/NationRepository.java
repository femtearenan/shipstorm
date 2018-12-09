package se.femtearenan.shipstorm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.femtearenan.shipstorm.model.Nation;

public interface NationRepository extends JpaRepository<Nation, Long> {
}
