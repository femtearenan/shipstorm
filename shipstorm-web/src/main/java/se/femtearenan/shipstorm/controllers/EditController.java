package se.femtearenan.shipstorm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import se.femtearenan.shipstorm.enumerations.ShipType;
import se.femtearenan.shipstorm.form.util.GenerateShip;
import se.femtearenan.shipstorm.model.Ship;
import se.femtearenan.shipstorm.model.ShipClass;
import se.femtearenan.shipstorm.model.ShipImage;
import se.femtearenan.shipstorm.services.NationService;
import se.femtearenan.shipstorm.services.ShipClassService;
import se.femtearenan.shipstorm.services.ShipService;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Controller
public class EditController {
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

    @RequestMapping("/shipstorm/ship/{id}/edit")
    public String getEditShip(@PathVariable Long id , Model model) {
        Ship ship = shipService.getShipById(id);
        model.addAttribute("ship", ship);
        model.addAttribute("classes", shipClassService.listAllShipClasses());
        model.addAttribute("nations", nationService.listAllNations());
        model.addAttribute("sensors", "");
        model.addAttribute("generateShip", new GenerateShip());
        model.addAttribute("types", ShipType.values());

        Map<String, String> images = new HashMap<>();
        for (ShipImage shipImage : ship.getShipImage()) {
            images.put(imageBlobToBase64String(shipImage.getImage()), shipImage.getDescription());
        }
        model.addAttribute("images", images);
        return "editShip";
    }

    private String imageBlobToBase64String(byte[] blob) {
        return Base64.getEncoder().encodeToString(blob);
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

        // Nation affiliation
        if (generateShip.getNationId() != null) {
            if (!generateShip.getNationId().equals(ship.getNation().getId())) {
                ship.setNation(nationService.getNationById(generateShip.getNationId()));
            }
        }

        shipService.save(ship);

        // Check if class variables been updated
        parseShipClass(generateShip, id);

        return "redirect:/shipstorm/ship/" + id + "/edit";
    }

    @RequestMapping( "/shipstorm/ship-class/{id}/edit")
    public String editShipClass(@PathVariable Long id, Model model) {
        ShipClass shipClass = shipClassService.getShipClassById(id);
        if (shipClass == null) {
            return "redirect:/shipstorm/";
        }
        model.addAttribute("shipClass", shipClass);
        model.addAttribute("types", ShipType.values());
        model.addAttribute("generateShip", new GenerateShip());
        return "editShipClass";
    }

    @RequestMapping(value = "/shipstorm/ship-class/{id}/edit", method = RequestMethod.POST)
    public String editShipClass(@PathVariable Long id, GenerateShip generateShip) {
        ShipClass shipClass = shipClassService.getShipClassById(id);
        System.out.println(generateShip.getShipClassName());
        System.out.println(generateShip.getShipType());
        shipClass = addChangedValues(shipClass, generateShip);
        if (generateShip.getShipClassName().length() > 0 || generateShip.getShipType() != null) {
            shipClassService.save(shipClass);
        }
        return "redirect:/shipstorm/ship-class/" + id + "/edit";
    }

    private void parseShipClass(GenerateShip generateShip, Long shipId) {
        Long shipClassId = generateShip.getShipClassId();
        Ship ship = shipService.getShipById(shipId);

        ShipClass shipClass;

        switch (generateShip.getShipClassUpdateType()) {
            case "update":
                shipClass = ship.getShipClass();
                shipClass = addChangedValues(shipClass, generateShip);
                if (generateShip.getShipClassName().length() > 0 || generateShip.getShipType() != null) {
                    shipClassService.save(shipClass);
                }
                break;
            case "change":
                if (shipClassId != null) {
                    shipClass = shipClassService.getShipClassById(shipClassId);
                    ship.setShipClass(shipClass);
                    shipService.save(ship);
                    shipClassService.save(shipClass);
                }
                break;
            case "add":
                shipClass = new ShipClass();
                if (generateShip.getShipClassName().length() > 0 && generateShip.getShipType() != null) {
                    shipClass.setName(generateShip.getShipClassName());
                    shipClass.setShipType(generateShip.getShipType());
                    shipClassService.save(shipClass);
                    ship.setShipClass(shipClass);
                    shipService.save(ship);
                }
                break;
            default:
        }

    }

    private ShipClass addChangedValues(ShipClass shipClass, GenerateShip generateShip) {
        if (generateShip.getShipClassName().length() > 0) {
            if (!generateShip.getShipClassName().equals(shipClass.getName())) {
                shipClass.setName(generateShip.getShipClassName());
            }
        }
        if (generateShip.getShipType() != null) {
            if (!shipClass.getShipType().equals(generateShip.getShipType())) {
                shipClass.setShipType(generateShip.getShipType());
            }
        }
        return shipClass;
    }
}
