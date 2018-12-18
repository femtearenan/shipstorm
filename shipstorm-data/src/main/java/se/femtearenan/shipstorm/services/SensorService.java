package se.femtearenan.shipstorm.services;

import se.femtearenan.shipstorm.enumerations.ShipType;
import se.femtearenan.shipstorm.model.Nation;
import se.femtearenan.shipstorm.model.Sensor;
import se.femtearenan.shipstorm.model.ShipClass;

import java.util.List;

public interface SensorService {
    Iterable<Sensor> listAllSensors();

    List<Sensor> findByName(String name);
    List<Sensor> findByNameContaining(String name);
    List<Sensor> findByType(String type);
    List<Sensor> findByFrequencyBand(String frequencyBand);
    List<Sensor> findByShipClass(ShipClass shipClass);
    List<Sensor> findByShipType(ShipType shipType);
    List<Sensor> findByNation(Nation nation);
    Sensor findTopByOrderByIdDesc();
}
