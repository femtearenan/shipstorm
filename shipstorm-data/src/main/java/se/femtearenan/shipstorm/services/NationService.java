package se.femtearenan.shipstorm.services;

import se.femtearenan.shipstorm.enumerations.ShipType;
import se.femtearenan.shipstorm.model.Nation;
import se.femtearenan.shipstorm.model.ShipClass;

import java.util.List;

public interface NationService {
    Iterable<Nation> listAllNations();
    Nation getNationById(Long id);
    Nation save(Nation nation);

    List<Nation> findByName(String name);
    List<Nation> findByNameContaining(String name);
    List<Nation> findByShipClassesShipType(ShipType shipType);
    List<Nation> findByShipsNameContaining(String name);
    List<Nation> findByShipsPennantContaining(String pennant);
    List<Nation> findByShipsShipClass(ShipClass shipClass);
    List<Nation> findByShipsSensorsNameContaining(String sensor);
}
