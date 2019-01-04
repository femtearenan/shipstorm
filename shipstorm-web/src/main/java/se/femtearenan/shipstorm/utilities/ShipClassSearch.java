package se.femtearenan.shipstorm.utilities;

import se.femtearenan.shipstorm.enumerations.ShipType;
import se.femtearenan.shipstorm.exceptions.ResultingListSizeException;
import se.femtearenan.shipstorm.model.Nation;
import se.femtearenan.shipstorm.model.Sensor;
import se.femtearenan.shipstorm.model.Ship;
import se.femtearenan.shipstorm.model.ShipClass;
import se.femtearenan.shipstorm.services.NationService;
import se.femtearenan.shipstorm.services.SensorService;
import se.femtearenan.shipstorm.services.ShipClassService;
import se.femtearenan.shipstorm.services.ShipService;

import java.util.*;

@SuppressWarnings("ALL")
public class ShipClassSearch {
    // Limit to number of JPA look-ups; example is a search for ships by supplying a ship-class
    // as a search parameter resulting in 6 or more ship-classes would result in 6 or more look-ups.
    private final int FIRST_TIER_LIST_LIMIT = 50;
    private final int SECOND_TIER_LIST_LIMIT = 5;

    private ShipService shipService;
    private NationService nationService;
    private ShipClassService shipClassService;
    private SensorService sensorService;

    public ShipClassSearch(ServicePackage servicePackage) {
        shipService = servicePackage.getShipService();
        nationService = servicePackage.getNationService();
        shipClassService = servicePackage.getShipClassService();
        sensorService = servicePackage.getSensorService();
    }

    public Set<ShipClass> searchShipClass(Map<String, String> searchStrings) throws Exception {

        List<ShipClass> result = new ArrayList<>();

        // Waterfall logic of JPA/Hibernate command to limit number of look-ups.
        if (searchStrings.get("ship").length() > 0) {
            List<ShipClass> shipClassByShipName = shipClassService.findByShipsNameContaining(searchStrings.get("ship"));
            if (shipClassByShipName.size() > 0) {
                searchStrings.remove("ship");
                result = filterList(shipClassByShipName, searchStrings);
            }

        } else if (searchStrings.get("pennant").length() > 0) {
            List<ShipClass> shipClassByPennant = shipClassService.findByShipsPennantContaining(searchStrings.get("pennant"));
            if (shipClassByPennant.size() > 0) {
                searchStrings.remove("pennant");
                result = filterList(shipClassByPennant, searchStrings);
            }

        } else if (searchStrings.get("class").length() > 0) {
            List<ShipClass> shipByClass = shipClassService.findByNameContaining(searchStrings.get("class"));
            if (shipByClass.size() > 0) {
                searchStrings.remove("class");
                result = filterList(shipByClass, searchStrings);
            }

        } else if (searchStrings.get("type").length() > 0) {
            ShipType shipType = ShipType.valueOf(searchStrings.get("type"));
            List<ShipClass> shipByType = shipClassService.findByType(shipType);
            if (shipByType.size() > 0) {
                searchStrings.remove("type");
                result = filterList(shipByType, searchStrings);
            }

        } else if (searchStrings.get("nation").length() > 0) {
            List<ShipClass> shipByNation = shipClassService.findByShipsNationNameContaining(searchStrings.get("nation"));
            if (shipByNation.size() > 0) {
                searchStrings.remove("nation");
                result = filterList(shipByNation, searchStrings);
            }

        } else if (searchStrings.get("sensor").length() > 0) {
            List<ShipClass> shipBySensor = shipClassService.findByShipsSensorsNameContaining(searchStrings.get("sensor"));
            if (shipBySensor.size() > 0) {
                searchStrings.remove("ship");
                result = filterList(shipBySensor, searchStrings);
            }

        } else if (searchStrings.get("any").length() > 0) {

            List<ShipClass> shipClasses = new ArrayList<>();

            List<ShipClass> shipClassByShipName = shipClassService.findByShipsNameContaining(searchStrings.get("ship"));
            List<ShipClass> shipClassByPennant = shipClassService.findByShipsPennantContaining(searchStrings.get("pennant"));
            List<ShipClass> shipByClass = shipClassService.findByNameContaining(searchStrings.get("class"));
            List<ShipClass> shipByType = new ArrayList<>();
            try {
                ShipType shipType = ShipType.valueOf(searchStrings.get("type"));
                shipByType = shipClassService.findByType(shipType);
            } catch (Exception e) {e.printStackTrace();}
            List<ShipClass> shipByNation = shipClassService.findByShipsNationNameContaining(searchStrings.get("nation"));
            List<ShipClass> shipBySensor = shipClassService.findByShipsSensorsNameContaining(searchStrings.get("sensor"));


            result = shipClasses;
        }

        return new HashSet<ShipClass>(result);
    }

    private List<Ship> shipsByAssociatedShipClass(String shipClassString, int limit) throws ResultingListSizeException {
        List<Ship> shipByClass = new ArrayList<>();
        List<ShipClass> shipClasses = shipClassService.findByNameContaining(shipClassString);
        if (shipClasses.size() > 0 && shipClasses.size() <= limit) {
            for (ShipClass shipClass : shipClasses) {
                shipByClass.addAll(shipService.findByShipClass(shipClass));
            }
        } else if (shipClasses.size() > limit) {
            throw new ResultingListSizeException("Resulting list exceeds set limit of " + limit + ".");
        }

        return shipByClass;
    }

    private List<Ship> shipsByAssociatedShipType(String shipTypeString) {
        List<Ship> shipByType = new ArrayList<>();
        try {
            ShipType shipType;
            for (ShipType shipType1 : ShipType.values()) {
                if (shipType1.getTypeDescription().contentEquals(shipTypeString)) {
                    shipType = shipType1;
                    shipByType = shipService.findByShipType(shipType);
                }
            }
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return shipByType;
    }

    private List<Ship> shipsByAssociatedNation(String nationString, int limit) throws ResultingListSizeException {
        List<Ship> shipByNation = new ArrayList<>();
        List<Nation> nations = nationService.findByNameContaining(nationString);
        if (nations.size() > 0 && nations.size() <= limit) {
            for (Nation nation : nations) {
                shipByNation.addAll(shipService.findByNation(nation));
            }

        } else if (nations.size() > limit) {
            throw new ResultingListSizeException("Resulting list exceeds set limit of " + limit + ".");
        }

        return shipByNation;
    }

    private List<Ship> shipsByAssociatedSensor(String sensorString, int limit) throws ResultingListSizeException {
        List<Ship> shipBySensor = new ArrayList<>();
        List<Sensor> sensors = sensorService.findByNameContaining(sensorString);
        if (sensors.size() > 0 && sensors.size() <= FIRST_TIER_LIST_LIMIT) {
            for (Sensor sensor : sensors) {
                shipBySensor.addAll(shipService.findBySensor(sensor));
            }
        } else if (sensors.size() > limit) {
            throw new ResultingListSizeException("Resulting list exceeds set limit of " + limit + ".");
        }

        return shipBySensor;
    }

    private List<ShipClass> filterList(List<ShipClass> shipClasses, Map<String, String> searchStrings) throws ResultingListSizeException {

        List<ShipClass> filteredList = shipClasses;
        Set<String> searchKeys = searchStrings.keySet();
        for (String key : searchKeys) {
            if (searchStrings.get(key).length() > 0) {
                switch (key) {
                    case "pennant":
                        filteredList = pennantFilter(filteredList, searchStrings.get(key));
                        break;
                    case "class":
                        filteredList = shipClassFilter(filteredList, searchStrings.get(key));
                        break;
                    case "type":
                        filteredList = shipTypeFilter(filteredList, searchStrings.get(key));
                        break;
                    case "nation":
                        filteredList = nationFilter(filteredList, searchStrings.get(key));
                        break;
                    case "sensor":
                        filteredList = sensorFilter(filteredList, searchStrings.get(key));
                        break;
                }
            }
        }

        return filteredList;
    }

    private List<ShipClass> pennantFilter(List<ShipClass> shipClasses, String pennant) {
        List<ShipClass> pennantFilterResult = new ArrayList<>();
        pennantFilterResult.addAll(shipClassService.findByShipsPennantContaining(pennant));
        pennantFilterResult.retainAll(shipClasses);
        return pennantFilterResult;
    }

    private List<ShipClass> shipClassFilter(List<ShipClass> shipClasses, String shipClassString) throws ResultingListSizeException {
        List<ShipClass> shipClassFilterResult = new ArrayList<>();
        shipClassFilterResult.addAll(shipClassService.findByName(shipClassString));
        shipClassFilterResult.retainAll(shipClasses);

        return shipClassFilterResult;
    }

    private List<ShipClass> shipTypeFilter(List<ShipClass> shipClasses, String shipTypeString) {
        List<ShipClass> shipTypeFilterResult = new ArrayList<>();

        try {
            ShipType shipType;
            for (ShipType shipType1 : ShipType.values()) {
                if (shipType1.getTypeDescription().contentEquals(shipTypeString)) {
                    shipType = shipType1;
                    shipTypeFilterResult.addAll(shipClassService.findByType(shipType));
                }
            }
            shipTypeFilterResult.retainAll(shipClasses);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        return shipTypeFilterResult;
    }

    private List<ShipClass> nationFilter(List<ShipClass> shipClasses, String nationString) throws ResultingListSizeException {
        List<ShipClass> nationFilterResult = new ArrayList<>();
        nationFilterResult.addAll(shipClassService.findByShipsNationNameContaining(nationString));
        nationFilterResult.retainAll(shipClasses);

        return nationFilterResult;
    }

    private List<ShipClass> sensorFilter(List<ShipClass> shipClasses, String sensorString) throws ResultingListSizeException {
        List<ShipClass> sensorFilterResult = new ArrayList<>();
        sensorFilterResult.addAll(shipClassService.findByShipsSensorsNameContaining(sensorString));
        sensorFilterResult.retainAll(shipClasses);

        return sensorFilterResult;
    }
}
