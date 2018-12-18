package se.femtearenan.shipstorm.services;

import se.femtearenan.shipstorm.enumerations.ShipType;
import se.femtearenan.shipstorm.model.Nation;
import se.femtearenan.shipstorm.model.Sensor;
import se.femtearenan.shipstorm.model.Ship;
import se.femtearenan.shipstorm.model.ShipClass;

import java.util.List;

public interface ShipService {
    Iterable<Ship> listAllShips();

    Ship getShipById(Long id);

    Ship save(Ship ship);

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

    void delete(Ship ship);

    Ship findTopByOrderByIdDesc();

}
