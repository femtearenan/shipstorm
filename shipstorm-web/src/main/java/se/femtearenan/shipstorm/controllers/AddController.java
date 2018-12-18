package se.femtearenan.shipstorm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import se.femtearenan.shipstorm.enumerations.ShipType;
import se.femtearenan.shipstorm.form.util.GenerateShip;
import se.femtearenan.shipstorm.model.Nation;
import se.femtearenan.shipstorm.model.Ship;
import se.femtearenan.shipstorm.model.ShipClass;
import se.femtearenan.shipstorm.services.NationService;
import se.femtearenan.shipstorm.services.ShipClassService;
import se.femtearenan.shipstorm.services.ShipService;

import java.util.EnumSet;

@Controller
public class AddController {
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

    @RequestMapping("/shipstorm/add")
    public String add(Model model) {
        model.addAttribute("ship", new Ship());
        model.addAttribute("shipClass", new ShipClass());
        model.addAttribute("nation", new Nation());
        model.addAttribute("generateShip", new GenerateShip());
        model.addAttribute("nations", nationService.listAllNations());
        model.addAttribute("classes", shipClassService.listAllShipClasses());
        EnumSet<ShipType> types = EnumSet.allOf(ShipType.class);
        model.addAttribute("types", types);
        return "shipform";
    }

    @RequestMapping(value = "/shipstorm/add-ship", method = RequestMethod.POST)
    public String saveShip(GenerateShip generateShip) {
        Ship ship = generateShip.buildShip(shipService, nationService, shipClassService);
        Ship savedShip = shipService.save(ship);
        return "redirect:/shipstorm/ship/" + savedShip.getId();
    }

    @RequestMapping(value = "/shipstorm/add-class", method = RequestMethod.POST)
    public String saveShipClass(ShipClass shipClass) {
        shipClassService.save(shipClass);
        return "redirect:/shipstorm/add";
    }

    @RequestMapping(value = "/shipstorm/add-nation", method = RequestMethod.POST)
    public String saveNation(Nation nation) {
        nationService.save(nation);
        return "redirect:/shipstorm/add";
    }



}
