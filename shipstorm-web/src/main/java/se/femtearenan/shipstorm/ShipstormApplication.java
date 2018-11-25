package se.femtearenan.shipstorm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import se.femtearenan.shipstorm.model.Nation;
import se.femtearenan.shipstorm.services.NationService;

@SpringBootApplication //(scanBasePackages = {se.femtearenan.shipstorm.})
public class ShipstormApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShipstormApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(NationService nationService) {
        return args -> {
            Nation sweden = new Nation();
            sweden.setAbbreviation("SE");
            sweden.setName("Sweden");
            nationService.save(sweden);
        };
    }

}
