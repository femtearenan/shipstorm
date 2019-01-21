package se.femtearenan.shipstorm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.femtearenan.shipstorm.enumerations.ShipType;
import se.femtearenan.shipstorm.model.Nation;
import se.femtearenan.shipstorm.repositories.NationRepository;

import java.util.List;

@Service
public class NationServiceImpl implements NationService {
    private NationRepository nationRepository;

    @Autowired
    public void setNationRepository(NationRepository nationRepository) {
        this.nationRepository = nationRepository;
    }

    @Override
    public Iterable<Nation> listAllNations() {
        return nationRepository.findAll();
    }

    @Override
    public Nation getNationById(Long id) {
        return nationRepository.findById(id).orElse(null);
    }

    @Override
    public Nation save(Nation nation) {
        return nationRepository.save(nation) ;
    }

    @Override
    public List<Nation> findByName(String name) {
        return nationRepository.findByName(name);
    }

    @Override
    public List<Nation> findByNameContaining(String name) {
        return nationRepository.findByNameContaining(name);
    }

    @Override
    public List<Nation> findByShipClassesShipType(ShipType shipType) {
        return nationRepository.findByShipClassesShipType(shipType);
    }

    @Override
    public List<Nation> findByShipsNameContaining(String name) {
        return nationRepository.findByShipsNameContaining(name);
    }

    @Override
    public List<Nation> findByShipsPennantContaining(String pennant) {
        return nationRepository.findByShipsPennantContaining(pennant);
    }

    @Override
    public List<Nation> findByShipClassesNameContaining(String name) {
        return nationRepository.findByShipClassesNameContaining(name);
    }

    @Override
    public List<Nation> findByShipsSensorsNameContaining(String sensor) {
        return nationRepository.findByShipsSensorsNameContaining(sensor);
    }
}
