package se.femtearenan.shipstorm.services;

import se.femtearenan.shipstorm.model.ShipImage;

public interface ShipImageService {
    Iterable<ShipImage> listAllShipImages();
    ShipImage getShipById(Long id);
    ShipImage save(ShipImage shipImage);
}
