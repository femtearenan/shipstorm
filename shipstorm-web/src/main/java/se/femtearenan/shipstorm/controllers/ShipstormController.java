package se.femtearenan.shipstorm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import se.femtearenan.shipstorm.model.Ship;
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
        return "shipform";
    }

    @RequestMapping(value = "/shipstorm/ship", method = RequestMethod.POST)
    public String save(Ship ship) {
        shipService.save(ship);
        return "redirect:/shipstorm/ship/" + ship.getId();
    }
}
