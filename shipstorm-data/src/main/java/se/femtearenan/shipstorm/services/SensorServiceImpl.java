package se.femtearenan.shipstorm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.femtearenan.shipstorm.enumerations.ShipType;
import se.femtearenan.shipstorm.model.Nation;
import se.femtearenan.shipstorm.model.Sensor;
import se.femtearenan.shipstorm.model.ShipClass;
import se.femtearenan.shipstorm.repositories.SensorRepository;

import java.util.List;

@Service
public class SensorServiceImpl implements SensorService {
    private SensorRepository sensorRepository;

    @Autowired
    public void setSensorRepository(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    public Iterable<Sensor> listAllSensors() {
        return sensorRepository.findAll();
    }

    @Override
    public List<Sensor> findByName(String name) {
        return sensorRepository.findByName(name);
    }

    @Override
    public List<Sensor> findByNameContaining(String name) {
        return sensorRepository.findByNameContaining(name);
    }

    @Override
    public List<Sensor> findByType(String type) {
        return sensorRepository.findByType(type);
    }

    @Override
    public List<Sensor> findByFrequencyBand(String frequencyBand) {
        return sensorRepository.findByFrequencyBand(frequencyBand);
    }

    @Override
    public List<Sensor> findByShipClass(ShipClass shipClass) {
        return sensorRepository.findByShipsShipClass(shipClass);
    }

    @Override
    public List<Sensor> findByShipType(ShipType shipType) {
        return sensorRepository.findByShipsShipClassShipType(shipType);
    }

    @Override
    public List<Sensor> findByNation(Nation nation) {
        return sensorRepository.findByShipsNation(nation);
    }

    @Override
    public Sensor findTopByOrderByIdDesc() {
        return sensorRepository.findTopByOrderByIdDesc();
    }
}
