package shipstorm.model;

public class ShipClassAlternateNames extends BaseEntity{

    //ManyToOne
    ShipClass shipClass;

    String alternateName;

    ShipClassAlternateNames() {}


    public String getAlternateName() {
        return alternateName;
    }

    public void setAlternateName(String alternateName) {
        this.alternateName = alternateName;
    }

    public ShipClass getShipClass() {
        return shipClass;
    }

    public void setShipClass(ShipClass shipClass) {
        this.shipClass = shipClass;
    }

}
