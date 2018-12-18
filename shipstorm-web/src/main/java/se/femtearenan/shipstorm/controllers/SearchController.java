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
    public String chooseSearchType() {
        return "searchMenu";
    }


    @RequestMapping(value = "/shipstorm/search/{searchType}")
    public String search(@PathVariable ("searchType") String searchType, Model model) {
        Queue<String> entityTypes = new ArrayDeque<>();
        entityTypes.add("ship");
        entityTypes.add("class");
        entityTypes.add("type");
        entityTypes.add("nation");
        entityTypes.add("pennant");
        entityTypes.add("sensor");
        entityTypes.add("any");

        model.addAttribute("entityType", entityTypes);

        return "search";
    }

    @RequestMapping(value="/shipstorm/search/{searchType}/", method = RequestMethod.GET, params={"ship", "class", "type", "nation", "pennant", "sensor", "any"} )
    public String searchResult(
            @PathVariable("searchType") String searchType,
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
                //shipResult = searchShip(searchStrings);
                model.addAttribute("result", shipResult);
                searchType = "Ship";
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

            if (shipByName.size() > 0) {
                result = filterList(shipByName, searchStrings);
            }

        } else if (searchStrings.get("pennant").length() > 0) {
            List<Ship> shipByPennant = shipService.findByPennant(searchStrings.get("pennant"));

            if (shipByPennant.size() > 0) {
                result = filterList(shipByPennant, searchStrings);
            }
        } else if (searchStrings.get("class").length() > 0) {
            List<Ship> shipByClass = new ArrayList<>();
            List<ShipClass> shipClasses = shipClassService.findByNameContaining(searchStrings.get("class"));
            if (shipClasses.size() > 0 && shipClasses.size() <= FIRST_TIER_LIST_LIMIT) {
                for (ShipClass shipClass : shipClasses) {
                    shipByClass.addAll(shipService.findByShipClass(shipClass));
                }
                if (shipByClass.size() > 0) {
                    result = filterList(shipByClass, searchStrings);
                }
            } else if (shipClasses.size() > FIRST_TIER_LIST_LIMIT) {
                throw new ResultingListSizeException("Resulting list exceeds set limit of " + FIRST_TIER_LIST_LIMIT + ".");
            }

        } else if (searchStrings.get("type").length() > 0) {
            List<Ship> shipByType = new ArrayList<>();
            String shipTypeString = searchStrings.get("type");
            try {
                ShipType shipType = ShipType.valueOf(shipTypeString);
                shipByType = shipService.findByShipType(shipType);
            } catch (IllegalArgumentException e){
                e.printStackTrace();
            }
            if (shipByType.size() > 0) {
                result = filterList(shipByType, searchStrings);
            }

        } else if (searchStrings.get("nation").length() > 0) {
            List<Ship> shipByNation = new ArrayList<>();
            List<Nation> nations = nationService.findByNameContaining(searchStrings.get("nation"));
            if (nations.size() > 0 && nations.size() <= FIRST_TIER_LIST_LIMIT) {
                for (Nation nation : nations) {
                    shipByNation.addAll(shipService.findByNation(nation));
                }
                if (shipByNation.size() > 0) {
                    result = filterList(shipByNation, searchStrings);
                }
            } else if (nations.size() > FIRST_TIER_LIST_LIMIT) {
                throw new ResultingListSizeException("Resulting list exceeds set limit of " + FIRST_TIER_LIST_LIMIT + ".");
            }

        } else if (searchStrings.get("sensor").length() > 0) {
            List<Ship> shipBySensor = new ArrayList<>();
            List<Sensor> sensors = sensorService.findByNameContaining(searchStrings.get("sensor"));
            if (sensors.size() > 0 && sensors.size() <= FIRST_TIER_LIST_LIMIT) {
                for (Sensor sensor : sensors) {
                    shipBySensor.addAll(shipService.findBySensor(sensor));
                }
            } else if (sensors.size() > FIRST_TIER_LIST_LIMIT) {
                throw new ResultingListSizeException("Resulting list exceeds set limit of " + FIRST_TIER_LIST_LIMIT + ".");
            }
        } else if (searchStrings.get("any").length() > 0) {

            List<Ship> ships = new ArrayList<>();

            List<Ship> shipByName = shipService.findByNameContaining(searchStrings.get("ship"));

            List<Ship> shipByPennant = shipService.findByPennant(searchStrings.get("pennant"));

            List<Ship> shipByClass = new ArrayList<>();
            List<ShipClass> shipClasses = shipClassService.findByNameContaining(searchStrings.get("class"));
            if (shipClasses.size() > 0 && shipClasses.size() <= SECOND_TIER_LIST_LIMIT) {
                for (ShipClass shipClass : shipClasses) {
                    shipByClass.addAll(shipService.findByShipClass(shipClass));
                }
            } else if (shipClasses.size() > SECOND_TIER_LIST_LIMIT) {
                throw new ResultingListSizeException("Resulting list exceeds set limit of " + SECOND_TIER_LIST_LIMIT + ".");
            }

            List<Ship> shipByType = new ArrayList<>();
            String shipTypeString = searchStrings.get("type");
            try {
                ShipType shipType = ShipType.valueOf(shipTypeString);
                shipByType = shipService.findByShipType(shipType);
            } catch (IllegalArgumentException e){
                e.printStackTrace();
            }

            List<Ship> shipByNation = new ArrayList<>();
            List<Nation> nations = nationService.findByNameContaining(searchStrings.get("nation"));
            if (nations.size() > 0 && nations.size() <= SECOND_TIER_LIST_LIMIT) {
                for (Nation nation : nations) {
                    shipByNation.addAll(shipService.findByNation(nation));
                }
            } else if (nations.size() > SECOND_TIER_LIST_LIMIT) {
                throw new ResultingListSizeException("Resulting list exceeds set limit of " + SECOND_TIER_LIST_LIMIT + ".");
            }

            ships.addAll(shipByName);
            ships.addAll(shipByPennant);
            ships.addAll(shipByClass);
            ships.addAll(shipByType);
            ships.addAll(shipByNation);
            result = ships;
        }


        return result;
    }

    private List<Ship> filterList(List<Ship> ships, Map<String, String> searchStrings) {
        return null;
    }

    private List<Ship> filterPennant(List<Ship> ships, String pennant) {
        List<Ship> filteredPennantResult = new ArrayList<>();
        for (Ship ship : ships) {
            if (ship.getPennant().contains(pennant)) {
                filteredPennantResult.add(ship);
            }
        }

        return filteredPennantResult;
    }
}
