package se.femtearenan.shipstorm.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String pennant;
    String name;
    String imo;
    String miscInfo;
    private Date created = new Date();
    private Date updated = new Date();

    @OneToMany(mappedBy = "ship")
    List<ShipAlternateNames> alternateNames;

    @ManyToOne
    Nation nation;

    @ManyToOne
    ShipClass shipClass;

    @ManyToMany
    List<Sensor> sensors;

    @ManyToMany
    List<Scrap> scraps;

    @OneToMany(mappedBy = "ship")
    List<ShipImage> shipImage;

    public Ship() {}

    @PreUpdate
    private void setLastUpdate(){
        this.updated = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPennant() {
        return pennant;
    }

    public void setPennant(String pennant) {
        this.pennant = pennant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImo() {
        return imo;
    }

    public void setImo(String imo) {
        this.imo = imo;
    }

    public String getMiscInfo() {
        return miscInfo;
    }

    public void setMiscInfo(String miscInfo) {
        this.miscInfo = miscInfo;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    public List<ShipAlternateNames> getAlternateNames() {
        return alternateNames;
    }

    public void setAlternateNames(List<ShipAlternateNames> alternateNames) {
        this.alternateNames = alternateNames;
    }

    public Nation getNation() {
        return nation;
    }

    public void setNation(Nation nation) {
        this.nation = nation;
    }

    public ShipClass getShipClass() {
        return shipClass;
    }

    public void setShipClass(ShipClass shipClass) {
        this.shipClass = shipClass;
    }

    public String getShipClassName() {
        return shipClass.getName();
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public List<Scrap> getScraps() {
        return scraps;
    }

    public void setScraps(List<Scrap> scraps) {
        this.scraps = scraps;
    }

    public List<ShipImage> getShipImage() {
        return shipImage;
    }

    public void setShipImage(List<ShipImage> shipImage) {
        this.shipImage = shipImage;
    }

    public void addShipImage(ShipImage shipImage) {
        if (this.shipImage == null) {
            this.shipImage = new ArrayList<>();
        }
        this.shipImage.add(shipImage);
    }
}
