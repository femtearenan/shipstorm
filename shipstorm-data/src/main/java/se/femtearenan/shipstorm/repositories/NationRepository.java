package se.femtearenan.shipstorm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.femtearenan.shipstorm.model.Nation;

import java.util.List;

public interface NationRepository extends JpaRepository<Nation, Long> {
    List<Nation> findByName(String name);
    List<Nation> findByNameContaining(String name);
}
