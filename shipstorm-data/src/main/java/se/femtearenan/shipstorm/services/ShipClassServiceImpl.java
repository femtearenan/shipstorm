package se.femtearenan.shipstorm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.femtearenan.shipstorm.enumerations.ShipType;
import se.femtearenan.shipstorm.model.ShipClass;
import se.femtearenan.shipstorm.repositories.ShipClassRepository;

import java.util.List;

@Service
public class ShipClassServiceImpl implements ShipClassService {
    private ShipClassRepository shipClassRepository;

    @Autowired
    public void setShipClassRepository(ShipClassRepository shipClassRepository) {
        this.shipClassRepository = shipClassRepository;
    }

    @Override
    public Iterable<ShipClass> listAllShipClasses() {
        return shipClassRepository.findAll();
    }

    @Override
    public ShipClass getShipClassById(Long id) {
        return shipClassRepository.findById(id).orElse(null);
    }

    @Override
    public ShipClass save(ShipClass shipClass) {
        return shipClassRepository.save(shipClass);
    }

    @Override
    public List<ShipClass> findByName(String name) {
        return shipClassRepository.findByName(name);
    }

    @Override
    public List<ShipClass> findByNameContaining(String name) {
        return shipClassRepository.findByNameContaining(name);
    }

    @Override
    public List<ShipClass> findByType(ShipType shipType) {
        return shipClassRepository.findByShipType(shipType);
    }

    @Override
    public List<ShipClass> findByShipsNameContaining(String name) {
        return shipClassRepository.findByShipsNameContaining(name);
    }

    @Override
    public List<ShipClass> findByShipsPennantContaining(String pennant) {
        return shipClassRepository.findByShipsPennantContaining(pennant);
    }

    @Override
    public List<ShipClass> findByShipsNationNameContaining(String nation) {
        return shipClassRepository.findByShipsNationNameContaining(nation);
    }

    @Override
    public List<ShipClass> findByShipsSensorsNameContaining(String sensor) {
        return shipClassRepository.findByShipsSensorsNameContaining(sensor);
    }
}
