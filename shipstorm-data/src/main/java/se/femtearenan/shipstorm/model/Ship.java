package se.femtearenan.shipstorm.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Ship {

    String pennant;
    String name;

    @OneToMany(mappedBy = "ship")
    List<ShipAlternateNames> alternateNames;

    @ManyToOne
    Nation nation;

    @ManyToOne
    ShipClass shipClass;

    @ManyToMany
    List<Scrap> scraps;

    public Ship() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPennant() {
        return pennant;
    }

    public void setPennant(String pennant) {
        this.pennant = pennant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ShipAlternateNames> getAlternateNames() {
        return alternateNames;
    }

    public void setAlternateNames(List<ShipAlternateNames> alternateNames) {
        this.alternateNames = alternateNames;
    }

    public Nation getNation() {
        return nation;
    }

    public void setNation(Nation nation) {
        this.nation = nation;
    }

    public ShipClass getShipClass() {
        return shipClass;
    }

    public void setShipClass(ShipClass shipClass) {
        this.shipClass = shipClass;
    }

    public String getShipClassName() {
        return shipClass.getName();
    }

    public List<Scrap> getScraps() {
        return scraps;
    }

    public void setScraps(List<Scrap> scraps) {
        this.scraps = scraps;
    }
}
