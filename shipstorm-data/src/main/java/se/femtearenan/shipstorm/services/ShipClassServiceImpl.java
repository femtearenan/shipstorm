package se.femtearenan.shipstorm.services;

import org.springframework.beans.factory.annotation.Autowired;
import se.femtearenan.shipstorm.model.ShipClass;
import se.femtearenan.shipstorm.repositories.ShipClassRepository;

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
}
