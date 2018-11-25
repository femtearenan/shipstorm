package se.femtearenan.shipstorm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import se.femtearenan.shipstorm.services.ShipService;

@Controller
public class ShipstormController {

    private ShipService shipService;

    @Autowired
    public void setShipService(ShipService shipService) {
        this.shipService = shipService;
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
}
