package se.femtearenan.shipstorm.form.util;

import se.femtearenan.shipstorm.model.Nation;
import se.femtearenan.shipstorm.model.Ship;
import se.femtearenan.shipstorm.model.ShipClass;
import se.femtearenan.shipstorm.services.NationService;
import se.femtearenan.shipstorm.services.ShipClassService;
import se.femtearenan.shipstorm.services.ShipService;

public class GenerateShip {
    Long id;

    Long shipId;
    Long shipClassId;
    Long nationId;
    String pennant;
    String name;

    public GenerateShip() {
    }

    public Ship buildShip(ShipService shipService, NationService nationService, ShipClassService shipClassService) {
        Ship ship = new Ship();

        ship.setName(name);

        if (pennant != null) {
            ship.setPennant(pennant);
        }

        if (shipClassId != null && shipClassId > -1) {
            ShipClass shipClass = new ShipClass();
            shipClass = shipClassService.getShipClassById(shipClassId);
            if (shipClass != null) {
                ship.setShipClass(shipClass);
            }
        }

        if (nationId != null) {
            Nation nation = nationService.getNationById(nationId);
            if (nation != null) {
                ship.setNation(nation);
            }
        }

        return ship;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShipId() {
        return shipId;
    }

    public void setShipId(Long shipId) {
        this.shipId = shipId;
    }

    public Long getShipClassId() {
        return shipClassId;
    }

    public void setShipClassId(Long shipClassId) {
        this.shipClassId = shipClassId;
    }

    public Long getNationId() {
        return nationId;
    }

    public void setNationId(Long nationId) {
        this.nationId = nationId;
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
}
