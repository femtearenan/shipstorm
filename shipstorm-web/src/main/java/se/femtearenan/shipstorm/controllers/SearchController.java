package se.femtearenan.shipstorm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import se.femtearenan.shipstorm.enumerations.ShipTypes;
import se.femtearenan.shipstorm.model.Ship;
import se.femtearenan.shipstorm.model.ShipClass;
import se.femtearenan.shipstorm.services.NationService;
import se.femtearenan.shipstorm.services.ShipClassService;
import se.femtearenan.shipstorm.services.ShipService;

import java.util.*;

@Controller
public class SearchController {

    private ShipService shipService;
    private NationService nationService;
    private ShipClassService shipClassService;

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
        entityTypes.add("any");

        model.addAttribute("entityType", entityTypes);

        return "search";
    }


    @RequestMapping(value="/shipstorm/search/{searchType}/", method = RequestMethod.GET, params={"entity", "searchString", "searchType"} )
    public String searchResult(
            @PathVariable("searchType") String searchType,
            @RequestParam("entity") String entity,
            @RequestParam("searchString") String searchString,
            Model model) {
        String message = "No ship matching the search criteria has been found.";
        boolean hasResult = false;
        List<Ship> shipResult = new ArrayList<>();
        List<ShipClass> classResult = new ArrayList<>();
        List<ShipTypes> typeResult = new ArrayList<>();

        switch (searchType) {
            case "ship":
                shipResult = shipService.findByNameContaining(searchString);
                model.addAttribute("result", shipResult);
                searchType = "Ship";
                break;
            case "class":
                classResult = shipClassService.findByNameContaining(searchString);
                model.addAttribute("result", classResult);
                searchType = "Class";
                break;
            case "type":
                if (searchString.length() > 0) {
                    for (ShipTypes type : ShipTypes.values()) {
                        if (type.toString().contains(searchString)) {
                            typeResult.add(type);
                        }
                    }
                } else {
                    typeResult.addAll(Arrays.asList(ShipTypes.values()));
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
