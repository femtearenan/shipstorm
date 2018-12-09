package se.femtearenan.shipstorm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.femtearenan.shipstorm.model.Ship;

import java.util.List;

public interface ShipRepository extends JpaRepository<Ship, Long> {
    List<Ship> findByName(String name);
    List<Ship> findByNameContaining(String name);
    List<Ship> findByPennant(String pennant);
    Ship findTopByOrderByIdDesc();
}
