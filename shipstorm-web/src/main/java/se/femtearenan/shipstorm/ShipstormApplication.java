package se.femtearenan.shipstorm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import se.femtearenan.shipstorm.enumerations.ShipTypes;
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

            Nation denmark = new Nation();
            denmark.setAbbreviation("DK");
            denmark.setName("Denmark");
            nationService.save(denmark);

            ShipClass malmo = new ShipClass();
            malmo.setName("Malmö");
            malmo.setType(ShipTypes.PB);
            shipClassService.save(malmo);

            Ship stockholm = new Ship();
            stockholm.setName("Stockholm");
            stockholm.setNation(sweden);
            stockholm.setShipClass(malmo);
            stockholm.setPennant("P11");
            shipService.save(stockholm);

            ShipClass visbyClass = new ShipClass();
            visbyClass.setName("Visby");
            visbyClass.setType(ShipTypes.K);
            shipClassService.save(visbyClass);

            Ship visby = new Ship();
            visby.setName("Stockholm");
            visby.setNation(sweden);
            visby.setShipClass(visbyClass);
            visby.setPennant("K31");
            shipService.save(visby);

            ShipClass thetis = new ShipClass();
            thetis.setName("Thetis");
            thetis.setType(ShipTypes.FF);
            shipClassService.save(thetis);

            Ship hvidbjornen = new Ship();
            hvidbjornen.setName("Hvidbjørnen");
            hvidbjornen.setNation(denmark);
            hvidbjornen.setShipClass(thetis);
            hvidbjornen.setPennant("F360");
            shipService.save(hvidbjornen);
        };
    }

}
