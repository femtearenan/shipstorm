package se.femtearenan.shipstorm.model;

import java.util.ArrayList;

public class ShipClass extends BaseEntity{

    String name;

    //OneToMany(mappedBy = "shipClass")
    ArrayList<ShipClassAlternateNames> alternativeNames;

    //OneToMany(mappedBy = "shipClass")
    ArrayList<Ship> ships;
}
