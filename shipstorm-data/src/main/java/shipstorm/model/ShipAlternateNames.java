package shipstorm.model;

public class ShipAlternateNames extends BaseEntity{

    //ManyToOne
    Ship ship;

    String alternateName;

    ShipAlternateNames() {}

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
