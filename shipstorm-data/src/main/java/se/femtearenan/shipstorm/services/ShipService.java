package se.femtearenan.shipstorm.services;

import se.femtearenan.shipstorm.model.Ship;

import java.util.List;

public interface ShipService {
    Iterable<Ship> listAllShips();

    Ship getShipById(Long id);

    Ship save(Ship ship);

    List<Ship> findByName(String name);

    List<Ship> findByNameContaining(String name);

    List<Ship> findByPennant(String pennant);
}
