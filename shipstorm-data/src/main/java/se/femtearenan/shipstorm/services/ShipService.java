package se.femtearenan.shipstorm.services;

import se.femtearenan.shipstorm.model.Ship;

public interface ShipService {
    Iterable<Ship> listAllShips();

    Ship getShipById(Long id);

    Ship save(Ship ship);
}
