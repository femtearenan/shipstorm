package se.femtearenan.shipstorm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.femtearenan.shipstorm.enumerations.ShipType;
import se.femtearenan.shipstorm.model.Nation;
import se.femtearenan.shipstorm.model.Sensor;
import se.femtearenan.shipstorm.model.Ship;
import se.femtearenan.shipstorm.model.ShipClass;

import java.util.List;

public interface ShipRepository extends JpaRepository<Ship, Long> {
    List<Ship> findByName(String name);
    List<Ship> findByNameContaining(String name);
    List<Ship> findByPennant(String pennant);
    List<Ship> findByShipClass(ShipClass shipClass);
    List<Ship> findByShipType(ShipType shipType);
    List<Ship> findByNation(Nation nation);
    List<Ship> findBySensor(Sensor sensor);
    List<Ship> findByNameAndPennantAndShipClassAndShipTypeAndNationAndSensor(
            String name,
            String pennant,
            ShipClass shipClass,
            ShipType shipType,
            Nation nation,
            Sensor sensor);
    Ship findTopByOrderByIdDesc();
}
