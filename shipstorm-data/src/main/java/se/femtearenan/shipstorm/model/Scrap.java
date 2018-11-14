package se.femtearenan.shipstorm.model;

import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
public class Scrap extends BaseEntity{

    //ManyToOne
    Ship shipMention;

    //ManyToOne
    ShipClass shipClassMention;

    String content;

    //OneToOne(mappedBy = "scrap")
    Source source;
    Timestamp timestamp;

    Scrap() {}

    public Ship getShipMention() {
        return shipMention;
    }

    public void setShipMention(Ship shipMention) {
        this.shipMention = shipMention;
    }

    public ShipClass getShipClassMention() {
        return shipClassMention;
    }

    public void setShipClassMention(ShipClass shipClassMention) {
        this.shipClassMention = shipClassMention;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
