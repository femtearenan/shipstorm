package se.femtearenan.shipstorm.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Sensor {

    String name;
    String type;
    String frequencyBand;

    @ManyToMany
    List<Ship> ships;

    public Sensor(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrequencyBand() {
        return frequencyBand;
    }

    public void setFrequencyBand(String frequencyBand) {
        this.frequencyBand = frequencyBand;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }
}
