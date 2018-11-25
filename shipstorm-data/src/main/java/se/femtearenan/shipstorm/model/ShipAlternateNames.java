package se.femtearenan.shipstorm.model;

import javax.persistence.*;

@Entity
public class ShipAlternateNames {

    @ManyToOne
    Ship ship;

    String alternateName;

    ShipAlternateNames() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlternateName() {
        return alternateName;
    }

    public void setAlternateName(String alternateName) {
        this.alternateName = alternateName;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

}
