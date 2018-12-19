package se.femtearenan.shipstorm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

@Controller
public class SearchController {

    private final int FIRST_TIER_LIST_LIMIT = 50;

    // Limit to number of JPA look-ups; example is a search for ships by supplying a ship-class
    // as a search parameter resulting in 6 or more ship-classes would result in 6 or more look-ups.
    private final int SECOND_TIER_LIST_LIMIT = 5;

    private ShipService shipService;
    private NationService nationService;
    private ShipClassService shipClassService;
    private SensorService sensorService;

    @Autowired
    public void setShipService(ShipService shipService) {
        this.shipService = shipService;
    }

    @Autowired
    public void setNationService(NationService nationService) {
        this.nationService = nationService;
    }

    @Autowired
    public void setShipClassService(ShipClassService shipClassService) {
        this.shipClassService = shipClassService;
    }

    @Autowired
    public void setSensorService(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @RequestMapping(value="/shipstorm/search")
    public String chooseSearchType(Model model) {
        /*
        Queue<String> entityTypes = new ArrayDeque<>();
        entityTypes.add("ship");
        entityTypes.add("class");
        entityTypes.add("type");
        entityTypes.add("nation");
        entityTypes.add("pennant");
        entityTypes.add("sensor");
        entityTypes.add("any");
        model.addAttribute("entityType", entityTypes);
        */

        model.addAttribute("nations", nationService.listAllNations());
        EnumSet<ShipType> types = EnumSet.allOf(ShipType.class);
        model.addAttribute("types", types);

        return "search";
    }

    @RequestMapping(value="/shipstorm/query", method = RequestMethod.GET, params={"searchType", "ship", "class", "type", "nation", "pennant", "sensor", "any"} )
    public String searchResult(
            @RequestParam("searchType") String searchType,
            @RequestParam("ship") String shipString,
            @RequestParam("class") String classString,
            @RequestParam("type") String typeString,
            @RequestParam("nation") String nationString,
            @RequestParam("pennant") String pennantString,
            @RequestParam("sensor") String sensorString,
            @RequestParam("any") String anyString,
            Model model) {

        String message = "No ship matching the search criteria has been found.";
        Map<String, String> searchStrings = new HashMap<>();
        searchStrings.put("ship", shipString);
        searchStrings.put("pennant", pennantString);
        searchStrings.put("class", classString);
        searchStrings.put("type", typeString);
        searchStrings.put("nation", nationString);
        searchStrings.put("sensor", sensorString);
        searchStrings.put("any", anyString);

        boolean hasResult = false;
        List<Ship> shipResult = new ArrayList<>();
        List<ShipClass> classResult = new ArrayList<>();
        List<ShipType> typeResult = new ArrayList<>();

        switch (searchType) {
            case "ship":
                System.out.println("Querying for ships.");
                try {
                    shipResult = searchShip(searchStrings);
                    System.out.println("Found: " + shipResult.size() + " number of ships.");
                    model.addAttribute("result", shipResult);
                    searchType = "Ship";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "class":
                classResult = shipClassService.findByNameContaining(classString);
                model.addAttribute("result", classResult);
                searchType = "Class";
                break;
            case "type":
                if (typeString.length() > 0) {
                    for (ShipType type : ShipType.values()) {
                        if (type.toString().contains(typeString)) {
                            typeResult.add(type);
                        }
                    }
                } else {
                    typeResult.addAll(Arrays.asList(ShipType.values()));
                }
                model.addAttribute("result", typeResult);
                searchType = "Type";
                break;
            case "nation":
                break;
            case "pennant":
                break;
            case "any":
                break;
            default:
                message = "An entity is not recognized.";
        }

        if (!shipResult.isEmpty() || !classResult.isEmpty() || !typeResult.isEmpty()) {
            hasResult = true;
        }

        model.addAttribute("hasResult", hasResult);
        model.addAttribute("resultType", searchType);

        return "results";
    }

    private List<Ship> searchShip(Map<String, String> searchStrings) throws Exception {
        List<Ship> result = new ArrayList<>();

        // Waterfall logic of JPA/Hibernate command to limit number of look-ups.
        if (searchStrings.get("ship").length() > 0) {
            List<Ship> shipByName = shipService.findByNameContaining(searchStrings.get("ship"));
            System.out.println("Initial result is: " + shipByName.size() + " number of ships.");
            if (shipByName.size() > 0) {
                searchStrings.remove("ship");
                result = filterList(shipByName, searchStrings);
            }

        } else if (searchStrings.get("pennant").length() > 0) {
            List<Ship> shipByPennant = shipService.findByPennant(searchStrings.get("pennant"));
            if (shipByPennant.size() > 0) {
                searchStrings.remove("pennant");
                result = filterList(shipByPennant, searchStrings);
            }

        } else if (searchStrings.get("class").length() > 0) {
            List<Ship> shipByClass = shipsByAssociatedShipClass(searchStrings.get("class"), FIRST_TIER_LIST_LIMIT);
            if (shipByClass.size() > 0) {
                searchStrings.remove("class");
                result = filterList(shipByClass, searchStrings);
            }

        } else if (searchStrings.get("type").length() > 0) {
            List<Ship> shipByType = shipsByAssociatedShipType(searchStrings.get("type"));
            if (shipByType.size() > 0) {
                searchStrings.remove("type");
                result = filterList(shipByType, searchStrings);
            }

        } else if (searchStrings.get("nation").length() > 0) {
            List<Ship> shipByNation = shipsByAssociatedNation(searchStrings.get("nation"), FIRST_TIER_LIST_LIMIT);
            if (shipByNation.size() > 0) {
                searchStrings.remove("nation");
                result = filterList(shipByNation, searchStrings);
            }

        } else if (searchStrings.get("sensor").length() > 0) {
            List<Ship> shipBySensor = shipsByAssociatedSensor(searchStrings.get("sensor"), FIRST_TIER_LIST_LIMIT);
            if (shipBySensor.size() > 0) {
                searchStrings.remove("ship");
                result = filterList(shipBySensor, searchStrings);
            }

        } else if (searchStrings.get("any").length() > 0) {

            List<Ship> ships = new ArrayList<>();

            List<Ship> shipByName = shipService.findByNameContaining(searchStrings.get("any"));

            List<Ship> shipByPennant = shipService.findByPennant(searchStrings.get("any"));

            List<Ship> shipByClass = shipsByAssociatedShipClass(searchStrings.get("any"), SECOND_TIER_LIST_LIMIT);

            List<Ship> shipByType = shipsByAssociatedShipType(searchStrings.get("any"));

            List<Ship> shipByNation = shipsByAssociatedNation(searchStrings.get("any"), SECOND_TIER_LIST_LIMIT);

            ships.addAll(shipByName);
            ships.addAll(shipByPennant);
            ships.addAll(shipByClass);
            ships.addAll(shipByType);
            ships.addAll(shipByNation);
            result = ships;
        }

        return result;
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

    private List<Ship> filterList(List<Ship> ships, Map<String, String> searchStrings) throws ResultingListSizeException {

        List<Ship> filteredList = ships;
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

    private List<Ship> pennantFilter(List<Ship> ships, String pennant) {
        List<Ship> pennantFilterResult = new ArrayList<>();
        for (Ship ship : ships) {
            if (ship.getPennant().contains(pennant)) {
                pennantFilterResult.add(ship);
            }
        }

        return pennantFilterResult;
    }

    private List<Ship> shipClassFilter(List<Ship> ships, String shipClassString) throws ResultingListSizeException {
        List<Ship> shipClassFilterResult = new ArrayList<>();
        List<Ship> shipByClass = shipsByAssociatedShipClass(shipClassString, SECOND_TIER_LIST_LIMIT);

        for (Ship ship : ships) {
            for (Ship filterShip : shipByClass) {
                if (ship.equals(filterShip)) {
                    shipClassFilterResult.add(ship);
                }
            }
        }
        return shipClassFilterResult;
    }

    private List<Ship> shipTypeFilter(List<Ship> ships, String shipTypeString) {
        List<Ship> shipTypeFilterResult = new ArrayList<>();
        List<Ship> shipByType = shipsByAssociatedShipType(shipTypeString);

        for (Ship ship : ships) {
            for (Ship filterShip : shipByType) {
                if (ship.equals(filterShip)) {
                    shipTypeFilterResult.add(ship);
                }
            }
        }

        return shipTypeFilterResult;
    }

    private List<Ship> nationFilter(List<Ship> ships, String nationString) throws ResultingListSizeException {
        List<Ship> nationFilterResult = new ArrayList<>();
        List<Ship> shipByNation = shipsByAssociatedNation(nationString, SECOND_TIER_LIST_LIMIT);

        for (Ship ship : ships) {
            for (Ship filterShip : shipByNation) {
                if (ship.equals(filterShip)) {
                    nationFilterResult.add(ship);
                }
            }
        }

        return nationFilterResult;
    }

    private List<Ship> sensorFilter(List<Ship> ships, String sensorString) throws ResultingListSizeException {
        List<Ship> sensorFilterResult = new ArrayList<>();
        List<Ship> shipBySensor = shipsByAssociatedSensor(sensorString, SECOND_TIER_LIST_LIMIT);

        for (Ship ship : ships) {
            for (Ship filterShip : shipBySensor) {
                if (ship.equals(filterShip)) {
                    sensorFilterResult.add(ship);
                }
            }
        }

        return sensorFilterResult;
    }
}
