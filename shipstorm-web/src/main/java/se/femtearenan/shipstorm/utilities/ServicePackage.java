package se.femtearenan.shipstorm.utilities;

import se.femtearenan.shipstorm.services.NationService;
import se.femtearenan.shipstorm.services.SensorService;
import se.femtearenan.shipstorm.services.ShipClassService;
import se.femtearenan.shipstorm.services.ShipService;

public class ServicePackage {
    private ShipService shipService;
    private NationService nationService;
    private ShipClassService shipClassService;
    private SensorService sensorService;

    public ServicePackage(ShipService shipService, NationService nationService, ShipClassService shipClassService, SensorService sensorService) {
        this.shipService = shipService;
        this.nationService = nationService;
        this.shipClassService = shipClassService;
        this.sensorService = sensorService;
    }

    public ShipService getShipService() {
        return shipService;
    }

    public void setShipService(ShipService shipService) {
        this.shipService = shipService;
    }

    public NationService getNationService() {
        return nationService;
    }

    public void setNationService(NationService nationService) {
        this.nationService = nationService;
    }

    public ShipClassService getShipClassService() {
        return shipClassService;
    }

    public void setShipClassService(ShipClassService shipClassService) {
        this.shipClassService = shipClassService;
    }

    public SensorService getSensorService() {
        return sensorService;
    }

    public void setSensorService(SensorService sensorService) {
        this.sensorService = sensorService;
    }
}
