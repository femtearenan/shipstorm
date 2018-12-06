package se.femtearenan.shipstorm.model;

import se.femtearenan.shipstorm.enumerations.ShipTypes;

import javax.persistence.*;
import java.util.List;

@Entity
public class ShipClass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String name;

    ShipTypes type;

    @OneToMany(mappedBy = "shipClass")
    List<ShipClassAlternateNames> alternativeNames;

    @OneToMany(mappedBy = "shipClass")
    List<Ship> ships;

    public ShipClass() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShipTypes getType() {
        return type;
    }

    public void setType(ShipTypes type) {
        this.type = type;
    }

    public List<ShipClassAlternateNames> getAlternativeNames() {
        return alternativeNames;
    }

    public void setAlternativeNames(List<ShipClassAlternateNames> alternativeNames) {
        this.alternativeNames = alternativeNames;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }
}
