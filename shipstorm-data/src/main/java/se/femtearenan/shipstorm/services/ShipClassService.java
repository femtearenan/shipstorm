package se.femtearenan.shipstorm.services;

import se.femtearenan.shipstorm.enumerations.ShipType;
import se.femtearenan.shipstorm.model.ShipClass;

import java.util.List;

public interface ShipClassService {
    Iterable<ShipClass> listAllShipClasses();

    ShipClass getShipClassById(Long id);

    ShipClass save(ShipClass shipClass);

    List<ShipClass> findByName(String name);

    List<ShipClass> findByNameContaining(String name);

    List<ShipClass> findByType(ShipType shipType);
}
