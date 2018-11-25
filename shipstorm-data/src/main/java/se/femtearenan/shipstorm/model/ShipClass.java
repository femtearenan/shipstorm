package se.femtearenan.shipstorm.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class ShipClass extends BaseEntity{

    String name;

    @OneToMany(mappedBy = "shipClass")
    List<ShipClassAlternateNames> alternativeNames;

    @OneToMany(mappedBy = "shipClass")
    List<Ship> ships;
}
