package se.femtearenan.shipstorm.model;

import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
public class ShipClass extends BaseEntity{

    String name;

    //OneToMany(mappedBy = "shipClass")
    ArrayList<ShipClassAlternateNames> alternativeNames;

    //OneToMany(mappedBy = "shipClass")
    ArrayList<Ship> ships;
}
