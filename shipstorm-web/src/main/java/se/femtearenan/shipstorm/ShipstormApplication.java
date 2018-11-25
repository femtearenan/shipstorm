package se.femtearenan.shipstorm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import se.femtearenan.shipstorm.model.Nation;
import se.femtearenan.shipstorm.model.Ship;
import se.femtearenan.shipstorm.model.ShipClass;
import se.femtearenan.shipstorm.services.NationService;
import se.femtearenan.shipstorm.services.ShipClassService;
import se.femtearenan.shipstorm.services.ShipService;

@SpringBootApplication //(scanBasePackages = {se.femtearenan.shipstorm.})
public class ShipstormApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShipstormApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(NationService nationService, ShipClassService shipClassService, ShipService shipService) {
        return args -> {
            Nation sweden = new Nation();
            sweden.setAbbreviation("SE");
            sweden.setName("Sweden");
            nationService.save(sweden);

            ShipClass boat = new ShipClass();
            boat.setName("Boat");
            shipClassService.save(boat);

            Ship buster = new Ship();
            buster.setName("Buster");
            buster.setNation(sweden);
            buster.setShipClass(boat);
            shipService.save(buster);
        };
    }

}
