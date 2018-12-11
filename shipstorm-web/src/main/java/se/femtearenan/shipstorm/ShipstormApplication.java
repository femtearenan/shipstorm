package se.femtearenan.shipstorm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import se.femtearenan.shipstorm.enumerations.ShipTypes;
import se.femtearenan.shipstorm.model.Nation;
import se.femtearenan.shipstorm.model.Ship;
import se.femtearenan.shipstorm.model.ShipClass;
import se.femtearenan.shipstorm.model.ShipImage;
import se.femtearenan.shipstorm.services.NationService;
import se.femtearenan.shipstorm.services.ShipClassService;
import se.femtearenan.shipstorm.services.ShipImageService;
import se.femtearenan.shipstorm.services.ShipService;

import java.io.File;
import java.io.FileInputStream;

@SpringBootApplication //(scanBasePackages = {se.femtearenan.shipstorm.})
public class ShipstormApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShipstormApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(NationService nationService, ShipClassService shipClassService,
                                  ShipService shipService, ShipImageService shipImageService) {
        return args -> {

            Nation sweden = new Nation();
            sweden.setAbbreviation("SE");
            sweden.setName("Sweden");
            nationService.save(sweden);

            Nation denmark = new Nation();
            denmark.setAbbreviation("DK");
            denmark.setName("Denmark");
            nationService.save(denmark);

            Nation finland = new Nation();
            finland.setAbbreviation("FI");
            finland.setName("Finland");
            nationService.save(finland);

            ShipClass malmo = new ShipClass();
            malmo.setName("Malmö");
            malmo.setType(ShipTypes.PB);
            shipClassService.save(malmo);

            Ship stockholm = new Ship();
            stockholm.setName("Stockholm");
            stockholm.setNation(sweden);
            stockholm.setShipClass(malmo);
            stockholm.setPennant("P11");
            //stockholm.addShipImage(shipImage);
            shipService.save(stockholm);

            Ship mmo = new Ship();
            mmo.setName("Malmö");
            mmo.setNation(sweden);
            mmo.setShipClass(malmo);
            mmo.setPennant("P12");
            shipService.save(mmo);

            ShipClass visbyClass = new ShipClass();
            visbyClass.setName("Visby");
            visbyClass.setType(ShipTypes.K);
            shipClassService.save(visbyClass);

            Ship visby = new Ship();
            visby.setName("Visby");
            visby.setNation(sweden);
            visby.setShipClass(visbyClass);
            visby.setPennant("K31");
            shipService.save(visby);

            Ship helsingborg = new Ship();
            helsingborg.setName("Helsingborg");
            helsingborg.setNation(sweden);
            helsingborg.setShipClass(visbyClass);
            helsingborg.setPennant("K32");
            shipService.save(helsingborg);

            ShipClass thetis = new ShipClass();
            thetis.setName("Thetis");
            thetis.setType(ShipTypes.FF);
            shipClassService.save(thetis);

            Ship vaddaren = new Ship();
            vaddaren.setName("Vædderen");
            vaddaren.setNation(denmark);
            vaddaren.setShipClass(thetis);
            vaddaren.setPennant("F359");
            shipService.save(vaddaren);

            Ship hvidbjornen = new Ship();
            hvidbjornen.setName("Hvidbjørnen");
            hvidbjornen.setNation(denmark);
            hvidbjornen.setShipClass(thetis);
            hvidbjornen.setPennant("F360");
            shipService.save(hvidbjornen);

            ShipClass hamina = new ShipClass();
            hamina.setName("Hamina");
            hamina.setType(ShipTypes.K);
            shipClassService.save(hamina);

            Ship hma = new Ship();
            hma.setName("Hamina");
            hma.setNation(finland);
            hma.setShipClass(hamina);
            hma.setPennant("80");
            shipService.save(hma);

            Ship tornio = new Ship();
            tornio.setName("Tornio");
            tornio.setNation(finland);
            tornio.setShipClass(hamina);
            tornio.setPennant("81");
            shipService.save(tornio);

            ShipImage shipImage1 = new ShipImage();
            byte[] bFile1 = imageByterOfStaticImages("simple_boat.png");

            shipImage1.setImage(bFile1);
            shipImage1.setDescription("Stockholm in all its might.");
            shipImage1.setShip(stockholm);

            ShipImage shipImage2 = new ShipImage();
            byte[] bFile2 = imageByterOfStaticImages("shipstorm.png");

            shipImage2.setImage(bFile2);
            shipImage2.setDescription("Shipstorm");
            shipImage2.setShip(stockholm);

            shipImageService.save(shipImage1);
            shipImageService.save(shipImage2);


        };
    }


    private byte[] imageByterOfStaticImages(String fileName) {
        File filePath = new File("");
        String path = filePath.getAbsolutePath() + "\\shipstorm-web\\src\\main\\resources\\static\\images\\" + fileName;
        File file = new File(path);
        byte[] bFile = new byte[(int)file.length()];
        try {
            FileInputStream fis = new FileInputStream(file);
            fis.read(bFile);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bFile;
    }

}
