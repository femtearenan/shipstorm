package se.femtearenan.shipstorm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import se.femtearenan.shipstorm.enumerations.ShipTypes;
import se.femtearenan.shipstorm.model.Ship;
import se.femtearenan.shipstorm.model.ShipClass;
import se.femtearenan.shipstorm.services.NationService;
import se.femtearenan.shipstorm.services.ShipClassService;
import se.femtearenan.shipstorm.services.ShipService;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

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


    @RequestMapping(value = "/shipstorm/search")
    public String search(Model model) {
        Queue<String> entityTypes = new ArrayDeque<>();
        entityTypes.add("Ship");
        entityTypes.add("Class");
        entityTypes.add("Type");
        entityTypes.add("Nation");
        entityTypes.add("Pennant");
        entityTypes.add("All");

        model.addAttribute("entityType", entityTypes);

        return "search";
    }


    @RequestMapping(value="/shipstorm/search", method = RequestMethod.GET, params={"entity", "searchString"} )
    public String searchResult(@RequestParam("entity") String entity,
                                             @RequestParam("searchString") String searchString, Model model) {
        String message = "No ship matching the search criteria has been found.";
        boolean hasResult = false;
        String resultType = "none";
        List<Ship> result = new ArrayList<>();
        List<ShipClass> classResult = new ArrayList<>();

        switch (entity) {
            case "Ship":
                result = shipService.findByNameContaining(searchString);
                model.addAttribute("result", result);
                resultType = "Ship";
                break;
            case "Class":
                classResult = shipClassService.findByNameContaining(searchString);
                model.addAttribute("result", classResult);
                resultType = "Class";
                break;
            case "Type":
                ShipTypes shipType = null;
                for (ShipTypes type : ShipTypes.values()) {
                    if (type.toString().contains(searchString)) {
                        shipType = type;
                        break;
                    }
                }
                classResult = shipClassService.findByType(shipType);
                model.addAttribute("result", classResult);
                resultType = "Class";
                break;
            case "Nation":
                break;
            case "Pennant":
                break;
            case "All":
                break;
            default:
                message = "An entity is not recognized.";
        }

        if (!result.isEmpty() || !classResult.isEmpty()) {
            hasResult = true;
        }

        model.addAttribute("hasResult", hasResult);
        model.addAttribute("resultType", resultType);

        return "results";
    }
}
