package se.femtearenan.shipstorm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import se.femtearenan.shipstorm.model.Ship;
import se.femtearenan.shipstorm.model.ShipImage;
import se.femtearenan.shipstorm.services.NationService;
import se.femtearenan.shipstorm.services.ShipClassService;
import se.femtearenan.shipstorm.services.ShipService;

import java.util.Base64;
import java.util.List;

@Controller
public class ShipController {
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

    @RequestMapping("/shipstorm/ship/{id}")
    public String showShip(@PathVariable Long id, Model model) {
        Ship ship = shipService.getShipById(id);
        List<ShipImage> shipImages = ship.getShipImage();
        byte[] blob = null;
        String image = "";
        String imageDescription = "";
        if (shipImages != null & shipImages.size() > 0) {
            ShipImage shipImage = shipImages.get(0);
            blob = shipImage.getImage();
            imageDescription = shipImage.getDescription();
            System.out.println(imageDescription);
            image = imageBlobToBase64String(blob);
        }
        model.addAttribute("ship", ship);
        model.addAttribute("nation", ship.getNation());
        model.addAttribute("class", ship.getShipClass());
        model.addAttribute("image", image);
        model.addAttribute("imageDescription", imageDescription);
        return "ship";
    }

    private String imageBlobToBase64String(byte[] blob) {
        return Base64.getEncoder().encodeToString(blob);
    }



}
