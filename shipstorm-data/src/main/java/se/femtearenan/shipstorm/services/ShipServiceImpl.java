package se.femtearenan.shipstorm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.femtearenan.shipstorm.enumerations.ShipType;
import se.femtearenan.shipstorm.model.Nation;
import se.femtearenan.shipstorm.model.Sensor;
import se.femtearenan.shipstorm.model.Ship;
import se.femtearenan.shipstorm.model.ShipClass;
import se.femtearenan.shipstorm.repositories.ShipRepository;

import java.util.List;

@Service
public class ShipServiceImpl implements ShipService {
    private ShipRepository shipRepository;

    @Autowired
    public void setShipRepository(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    @Override
    public Iterable<Ship> listAllShips() {
        return shipRepository.findAll();
    }

    @Override
    public Ship getShipById(Long id) {
        return shipRepository.findById(id).orElse(null);
    }

    @Override
    public Ship save(Ship ship) {
        return shipRepository.save(ship);
    }

    @Override
    public List<Ship> findByName(String name) {
        return shipRepository.findByName(name);
    }

    @Override
    public List<Ship> findByNameContaining(String name) {
        return shipRepository.findByNameContaining(name);
    }

    @Override
    public List<Ship> findByPennant(String pennant) {
        return shipRepository.findByPennant(pennant);
    }

    @Override
    public List<Ship> findByShipClass(ShipClass shipClass) {
        return shipRepository.findByShipClass(shipClass);
    }

    @Override
    public List<Ship> findByShipType(ShipType shipType) {
        return shipRepository.findByShipType(shipType);
    }

    @Override
    public List<Ship> findByNation(Nation nation) {
        return shipRepository.findByNation(nation);
    }

    @Override
    public List<Ship> findBySensor(Sensor sensor) {
        return shipRepository.findBySensor(sensor);
    }

    @Override
    public List<Ship> findByNameAndPennantAndShipClassAndShipTypeAndNationAndSensor(String name, String pennant, ShipClass shipClass, ShipType shipType, Nation nation, Sensor sensor) {
        return shipRepository.findByNameAndPennantAndShipClassAndShipTypeAndNationAndSensor(name, pennant, shipClass, shipType, nation, sensor);
    }

    @Override
    public Ship findTopByOrderByIdDesc() {
        return shipRepository.findTopByOrderByIdDesc();
    }

    @Override
    public void delete(Ship ship) {
        shipRepository.delete(ship);
    }
}
