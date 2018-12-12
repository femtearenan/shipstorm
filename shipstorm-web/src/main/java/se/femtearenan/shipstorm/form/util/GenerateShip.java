package se.femtearenan.shipstorm.form.util;

import se.femtearenan.shipstorm.enumerations.ShipTypes;
import se.femtearenan.shipstorm.model.Nation;
import se.femtearenan.shipstorm.model.Ship;
import se.femtearenan.shipstorm.model.ShipClass;
import se.femtearenan.shipstorm.services.NationService;
import se.femtearenan.shipstorm.services.ShipClassService;
import se.femtearenan.shipstorm.services.ShipService;

public class GenerateShip {
    Long id;

    // Ship variables
    Long shipId;
    String pennant;
    String name;
    String imo;
    String miscInfo;

    // ShipClass variables
    ShipClass shipClass;
    Long shipClassId;
    String shipClassName;
    ShipTypes shipType;
    String shipClassUpdateType;

    // Nation variables
    Long nationId;
    String nationName;
    String nationAbbreviation;

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

    public ShipClass getShipClass() {
        return shipClass;
    }

    public void setShipClass(ShipClass shipClass) {
        this.shipClass = shipClass;
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

    public String getShipClassUpdateType() {
        return shipClassUpdateType;
    }

    public void setShipClassUpdateType(String shipClassUpdateType) {
        this.shipClassUpdateType = shipClassUpdateType;
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

    public String getImo() {
        return imo;
    }

    public void setImo(String imo) {
        this.imo = imo;
    }

    public String getMiscInfo() {
        return miscInfo;
    }

    public void setMiscInfo(String miscInfo) {
        this.miscInfo = miscInfo;
    }

    public String getShipClassName() {
        return shipClassName;
    }

    public void setShipClassName(String shipClassName) {
        this.shipClassName = shipClassName;
    }

    public ShipTypes getShipType() {
        return shipType;
    }

    public void setShipType(ShipTypes shipType) {
        this.shipType = shipType;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    public String getNationAbbreviation() {
        return nationAbbreviation;
    }

    public void setNationAbbreviation(String nationAbbreviation) {
        this.nationAbbreviation = nationAbbreviation;
    }
}
