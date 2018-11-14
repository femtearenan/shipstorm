package se.femtearenan.shipstorm.model;

import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
public class Ship extends BaseEntity{

    String pennant;
    String name;

    //OneToMany(mappedBy = "ship")
    ArrayList<ShipAlternateNames> alternateNames;

    //ManyToOne
    Nation nation;

    //ManyToOne
    ShipClass shipClass;

    //OneToMany(mappedBy = "ship")
    ArrayList<Scrap> scraps;

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

    public ArrayList<ShipAlternateNames> getAlternateNames() {
        return alternateNames;
    }

    public void setAlternateNames(ArrayList<ShipAlternateNames> alternateNames) {
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

    public ArrayList<Scrap> getScraps() {
        return scraps;
    }

    public void setScraps(ArrayList<Scrap> scraps) {
        this.scraps = scraps;
    }
}
