package se.femtearenan.shipstorm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.femtearenan.shipstorm.model.ShipImage;
import se.femtearenan.shipstorm.repositories.ShipImageRepository;

@Service
public class ShipImageServiceImpl implements ShipImageService {
    private ShipImageRepository shipImageRepository;

    @Autowired
    public void setShipImageRepository(ShipImageRepository shipImageRepository) {
        this.shipImageRepository = shipImageRepository;
    }

    @Override
    public Iterable<ShipImage> listAllShipImages() {
        return shipImageRepository.findAll();
    }

    @Override
    public ShipImage getShipById(Long id) {
        return shipImageRepository.findById(id).orElse(null);
    }

    @Override
    public ShipImage save(ShipImage shipImage) {
        return shipImageRepository.save(shipImage);
    }
}
