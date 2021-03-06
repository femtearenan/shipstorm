package se.femtearenan.shipstorm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.femtearenan.shipstorm.enumerations.ShipType;
import se.femtearenan.shipstorm.model.Nation;
import se.femtearenan.shipstorm.model.Sensor;
import se.femtearenan.shipstorm.model.ShipClass;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    List<Sensor> findByName(String name);
    List<Sensor> findByNameContaining(String name);
    List<Sensor> findByType(String type);
    List<Sensor> findByFrequencyBand(String frequencyBand);
    List<Sensor> findByShipsShipClass(ShipClass shipClass);
    List<Sensor> findByShipsShipClassShipType(ShipType shipType);
    List<Sensor> findByShipsNation(Nation nation);
    Sensor findTopByOrderByIdDesc();
}
