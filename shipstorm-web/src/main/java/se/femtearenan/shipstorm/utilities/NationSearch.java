package se.femtearenan.shipstorm.utilities;

import se.femtearenan.shipstorm.enumerations.ShipType;
import se.femtearenan.shipstorm.exceptions.ResultingListSizeException;
import se.femtearenan.shipstorm.model.Nation;
import se.femtearenan.shipstorm.model.ShipClass;
import se.femtearenan.shipstorm.services.NationService;
import se.femtearenan.shipstorm.services.SensorService;
import se.femtearenan.shipstorm.services.ShipClassService;
import se.femtearenan.shipstorm.services.ShipService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("ALL")
public class NationSearch {
    private ShipService shipService;
    private NationService nationService;
    private ShipClassService shipClassService;
    private SensorService sensorService;

    public NationSearch(ServicePackage servicePackage) {
        shipService = servicePackage.getShipService();
        nationService = servicePackage.getNationService();
        shipClassService = servicePackage.getShipClassService();
        sensorService = servicePackage.getSensorService();
    }

    public List<Nation> searchNation(Map<String, String> searchStrings) throws Exception {

        List<Nation> result = new ArrayList<>();

        // Waterfall logic of JPA/Hibernate command to limit number of look-ups.
        if (searchStrings.get("ship").length() > 0) {
            List<Nation> nationByShipName = nationService.findByShipsNameContaining(searchStrings.get("ship"));
            if (nationByShipName.size() > 0) {
                searchStrings.remove("ship");
                result = filterList(nationByShipName, searchStrings);
            }

        } else if (searchStrings.get("pennant").length() > 0) {
            List<Nation> nationsByPennant =  nationService.findByShipsPennantContaining(searchStrings.get("pennant"));
            if (nationsByPennant.size() > 0) {
                searchStrings.remove("pennant");
                result = filterList(nationsByPennant, searchStrings);
            }

        } else if (searchStrings.get("class").length() > 0) {
            List<ShipClass> shipClasses = shipClassService.findByNameContaining(searchStrings.get("class"));
            List<Nation> nationsByShipClass = new ArrayList<>();
            for (ShipClass shipClass : shipClasses) {
                nationsByShipClass.addAll(nationService.findByShipsShipClass(shipClass));
            }
            if (nationsByShipClass.size() > 0) {
                searchStrings.remove("class");
                result = filterList(nationsByShipClass, searchStrings);
            }

        } else if (searchStrings.get("type").length() > 0) {
            ShipType shipType;
            List<Nation> nationsByType = new ArrayList<>();
            try {
                for (ShipType shipType1 : ShipType.values()) {
                    if (shipType1.getTypeDescription().contentEquals(searchStrings.get("type"))) {
                        shipType = shipType1;
                        System.out.println("Searching by type."); //TODO: remove
                        nationsByType = nationService.findByShipClassesShipType(shipType);
                    }
                }
            } catch (IllegalArgumentException e){
                e.printStackTrace();
            }
            if (nationsByType.size() > 0) {
                searchStrings.remove("type");
                result = filterList(nationsByType, searchStrings);
            }

        } else if (searchStrings.get("nation").length() > 0) {
            List<Nation> nationsByName = nationService.findByNameContaining(searchStrings.get("nation"));
            if (nationsByName.size() > 0) {
                searchStrings.remove("nation");
                result = filterList(nationsByName, searchStrings);
            }

        } else if (searchStrings.get("sensor").length() > 0) {
            List<Nation> nationsBySensor = nationService.findByShipsSensorsNameContaining(searchStrings.get("sensor"));
            if (nationsBySensor.size() > 0) {
                searchStrings.remove("ship");
                result = filterList(nationsBySensor, searchStrings);
            }

        } else if (searchStrings.get("any").length() > 0) {


            List<Nation> nations = new ArrayList<>();

            List<Nation> nationsByShipName = nationService.findByShipsNameContaining(searchStrings.get("ship"));
            List<Nation> nationsByShipPennant = nationService.findByShipsPennantContaining(searchStrings.get("pennant"));
            List<ShipClass> shipClasses = shipClassService.findByNameContaining(searchStrings.get("class"));
            List<Nation> nationsByShipClass = new ArrayList<>();
            for (ShipClass shipClass : shipClasses) {
                nationsByShipClass.addAll(nationService.findByShipsShipClass(shipClass));
            }
            List<Nation> nationsByType = new ArrayList<>();
            try {
                ShipType shipType = ShipType.valueOf(searchStrings.get("type"));
                nationsByType = nationService.findByShipClassesShipType(shipType);
            } catch (Exception e) {e.printStackTrace();}
            List<Nation> nationsByName = nationService.findByNameContaining(searchStrings.get("nation"));
            List<Nation> nationsBySensor = nationService.findByShipsSensorsNameContaining(searchStrings.get("sensor"));

            result = nations;
        }

        return result;
    }

    private List<Nation> filterList(List<Nation> nations, Map<String, String> searchStrings) throws ResultingListSizeException {

        List<Nation> filteredList = nations;
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

    private List<Nation> pennantFilter(List<Nation> nations, String pennant) {
        List<Nation> pennantFilterResult = new ArrayList<>();
        pennantFilterResult.addAll(nationService.findByShipsPennantContaining(pennant));
        pennantFilterResult.retainAll(nations);
        return pennantFilterResult;
    }

    private List<Nation> shipClassFilter(List<Nation> nations, String shipClassString) throws ResultingListSizeException {
        List<Nation> shipClassFilterResult = new ArrayList<>();
        List<ShipClass> shipClasses = shipClassService.findByNameContaining(shipClassString);
        List<Nation> nationsByShipClass = new ArrayList<>();
        for (ShipClass shipClass : shipClasses) {
            nationsByShipClass.addAll(nationService.findByShipsShipClass(shipClass));
        }
        shipClassFilterResult.retainAll(nations);

        return shipClassFilterResult;
    }

    private List<Nation> shipTypeFilter(List<Nation> nations, String shipTypeString) {
        List<Nation> shipTypeFilterResult = new ArrayList<>();

        try {
            ShipType shipType;
            for (ShipType shipType1 : ShipType.values()) {
                if (shipType1.getTypeDescription().contentEquals(shipTypeString)) {
                    System.out.println("Search class by type: " + shipType1.getTypeDescription());
                    shipType = shipType1;
                    shipTypeFilterResult.addAll(nationService.findByShipClassesShipType(shipType));
                }
            }
            shipTypeFilterResult.retainAll(nations);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        return shipTypeFilterResult;
    }

    private List<Nation> nationFilter(List<Nation> nations, String nationString) throws ResultingListSizeException {
        List<Nation> nationFilterResult = new ArrayList<>();
        nationFilterResult.addAll(nationService.findByNameContaining(nationString));
        nationFilterResult.retainAll(nations);

        return nationFilterResult;
    }

    private List<Nation> sensorFilter(List<Nation> nations, String sensorString) throws ResultingListSizeException {
        List<Nation> sensorFilterResult = new ArrayList<>();
        sensorFilterResult.addAll(nationService.findByShipsSensorsNameContaining(sensorString));
        sensorFilterResult.retainAll(nations);

        return sensorFilterResult;
    }
}
