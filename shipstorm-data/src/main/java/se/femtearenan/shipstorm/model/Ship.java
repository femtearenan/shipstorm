package se.femtearenan.shipstorm.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Ship extends BaseEntity{

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

    public List<Scrap> getScraps() {
        return scraps;
    }

    public void setScraps(List<Scrap> scraps) {
        this.scraps = scraps;
    }
}
