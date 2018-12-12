package se.femtearenan.shipstorm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import se.femtearenan.shipstorm.enumerations.ShipTypes;
import se.femtearenan.shipstorm.form.util.GenerateShip;
import se.femtearenan.shipstorm.model.Ship;
import se.femtearenan.shipstorm.model.ShipImage;
import se.femtearenan.shipstorm.services.NationService;
import se.femtearenan.shipstorm.services.ShipClassService;
import se.femtearenan.shipstorm.services.ShipService;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String imageNumerate = "0";
        byte[] blob = null;
        String image = "";
        String imageDescription = "";
        if (shipImages != null & shipImages.size() > 0) {
            ShipImage shipImage = shipImages.get(0);
            blob = shipImage.getImage();
            imageDescription = shipImage.getDescription();
            image = imageBlobToBase64String(blob);
            if (shipImages.size() == 1) {
                imageNumerate = "1 image";
            } else {
                imageNumerate = shipImages.size() + " images";
            }
        }
        model.addAttribute("ship", ship);
        model.addAttribute("nation", ship.getNation());
        model.addAttribute("class", ship.getShipClass());
        model.addAttribute("image", image);
        model.addAttribute("numberImages", imageNumerate);
        model.addAttribute("imageDescription", imageDescription);
        return "ship";
    }

    private String imageBlobToBase64String(byte[] blob) {
        return Base64.getEncoder().encodeToString(blob);
    }

    @RequestMapping("/shipstorm/ship/{id}/edit")
    public String getEditShip(@PathVariable Long id , Model model) {
        Ship ship = shipService.getShipById(id);
        model.addAttribute("ship", ship);
        model.addAttribute("classes", shipClassService.listAllShipClasses());
        model.addAttribute("nations", nationService.listAllNations());
        model.addAttribute("sensors", "");
        model.addAttribute("generateShip", new GenerateShip());
        model.addAttribute("types", ShipTypes.values());

        Map<String, String> images = new HashMap<>();
        for (ShipImage shipImage : ship.getShipImage()) {
            images.put(imageBlobToBase64String(shipImage.getImage()), shipImage.getDescription());
        }
        model.addAttribute("images", images);
        return "editShip";
    }

    @RequestMapping(value = "/shipstorm/ship/{id}/edit", method = RequestMethod.POST)
    public String editShip(@PathVariable Long id, GenerateShip generateShip) {
        Ship ship = shipService.getShipById(id);

        // UPDATE SHIP VARIABLES
        // Ship name
        if (generateShip.getName() != null) {
            if (generateShip.getName().length() > 0 & !generateShip.getName().equals(ship.getName())) {
                ship.setName(generateShip.getName());
            }
        }

        // Ship pennant
        if (generateShip.getPennant() != null) {
            if (generateShip.getPennant().length() > 0 & !generateShip.getPennant().equals(ship.getPennant())) {
                ship.setPennant(generateShip.getPennant());
            }
        }

        // Ship IMO
        if (generateShip.getImo() != null) {
            if (generateShip.getImo().length() > 0 & !generateShip.getImo().equals(ship.getImo())) {
                ship.setImo(generateShip.getImo());
            }
        }

        // Ship misc info
        if (generateShip.getMiscInfo() != null) {
            if (!generateShip.getMiscInfo().equals(ship.getMiscInfo())) {
                ship.setMiscInfo(generateShip.getMiscInfo());
            }
        }
        shipService.save(ship);


        return "redirect:/shipstorm/ship/" + id + "/edit";
    }



}
