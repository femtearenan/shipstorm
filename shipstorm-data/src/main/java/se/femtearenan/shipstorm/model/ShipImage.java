package se.femtearenan.shipstorm.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ShipImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(name = "photo", columnDefinition = "BLOB")
    private byte[] image;

    @ManyToOne
    Ship ship;

    private Date created = new Date();
    private Date updated = new Date();

    public ShipImage(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    @PreUpdate
    private void setLastUpdate(){
        this.updated = new Date();
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }
}
