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
import se.femtearenan.shipstorm.utilities.ServicePackage;
import se.femtearenan.shipstorm.utilities.ShipSearch;

import java.util.*;

@Controller
public class SearchController {

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
                    ServicePackage servicePackage = new ServicePackage(shipService, nationService,  shipClassService, sensorService);
                    ShipSearch shipSearch = new ShipSearch(servicePackage);
                    shipResult = shipSearch.searchShip(searchStrings);
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


}
