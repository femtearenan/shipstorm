package se.femtearenan.shipstorm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import se.femtearenan.shipstorm.model.Nation;
import se.femtearenan.shipstorm.model.Ship;
import se.femtearenan.shipstorm.model.ShipClass;
import se.femtearenan.shipstorm.services.NationService;
import se.femtearenan.shipstorm.services.ShipClassService;
import se.femtearenan.shipstorm.services.ShipService;

@Controller
public class ShipstormController {

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

    @RequestMapping({"/shipstorm"})
    public String getShipstormPage() {

        return "shipstorm";
    }

    @RequestMapping("/shipstorm/ships")
    public String list(Model model) {
        model.addAttribute("ships", shipService.listAllShips());
        return "ships";
    }

    @RequestMapping("/shipstorm/ship/{id}")
    public String showShip(@PathVariable Long id, Model model) {
        model.addAttribute("ship", shipService.getShipById(id));
        return "shipshow";
    }

    @RequestMapping("/shipstorm/ship/add")
    public String add(Model model) {
        model.addAttribute("ship", new Ship());
        model.addAttribute("shipClass", new ShipClass());
        model.addAttribute("nation", new Nation());
        model.addAttribute("nations", nationService.listAllNations());
        model.addAttribute("classes", shipClassService.listAllShipClasses());
        return "shipform";
    }

    @RequestMapping(value = "/shipstorm/ship/add-ship", method = RequestMethod.POST)
    public String saveShip(Ship ship) {
        shipService.save(ship);
        return "redirect:/ship/" + ship.getId();
    }

    @RequestMapping(value = "/shipstorm/ship/add-class", method = RequestMethod.POST)
    public String saveShipClass(ShipClass shipClass) {
        shipClassService.save(shipClass);
        return "redirect:/shipstorm/ship/add";
    }

    @RequestMapping(value = "/shipstorm/ship/nation", method = RequestMethod.POST)
    public String saveNation(Nation nation) {
        nationService.save(nation);
        return "redirect:/shipstorm/ship/add";
    }
}
