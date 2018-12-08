package se.femtearenan.shipstorm.repositories;

import org.springframework.data.repository.CrudRepository;
import se.femtearenan.shipstorm.model.Ship;

import java.util.List;

public interface ShipRepository extends CrudRepository<Ship, Long> {
    List<Ship> findByName(String name);
    List<Ship> findByNameContaining(String name);
    List<Ship> findByPennant(String pennant);
}
