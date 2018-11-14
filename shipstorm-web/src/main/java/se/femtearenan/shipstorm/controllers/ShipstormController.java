package se.femtearenan.shipstorm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShipstormController {
    @RequestMapping({"/shipstorm"})
    public String getShipstormPage() {

        return "shipstorm";
    }
}
