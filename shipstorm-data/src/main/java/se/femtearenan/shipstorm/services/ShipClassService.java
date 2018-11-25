package se.femtearenan.shipstorm.services;

import se.femtearenan.shipstorm.model.ShipClass;

public interface ShipClassService {
    Iterable<ShipClass> listAllShipClasses();

    ShipClass getShipClassById(Long id);

    ShipClass save(ShipClass shipClass);
}
