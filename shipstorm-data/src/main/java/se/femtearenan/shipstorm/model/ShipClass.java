package se.femtearenan.shipstorm.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class ShipClass {

    String name;

    @OneToMany(mappedBy = "shipClass")
    List<ShipClassAlternateNames> alternativeNames;

    @OneToMany(mappedBy = "shipClass")
    List<Ship> ships;

    public ShipClass() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
