package se.femtearenan.shipstorm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.femtearenan.shipstorm.model.ShipImage;

public interface ShipImageRepository extends JpaRepository<ShipImage, Long> {
}
