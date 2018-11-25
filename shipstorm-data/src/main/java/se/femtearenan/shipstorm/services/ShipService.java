package se.femtearenan.shipstorm.services;

import se.femtearenan.shipstorm.model.Ship;

public interface ShipService {
    Iterable<Ship> listAllShipClasses();

    Ship getShipClassById(Long id);

    Ship save(Ship ship);
}
