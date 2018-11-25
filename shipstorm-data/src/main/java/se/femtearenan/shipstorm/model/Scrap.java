package se.femtearenan.shipstorm.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Scrap {

    @ManyToMany
    List<Ship> shipMentions;

    @ManyToOne
    ShipClass shipClassMention;

    String content;

    @ManyToOne
    Source source;

    Timestamp timestamp;

    Scrap() {}

    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Ship> getShipMentions() {
        return shipMentions;
    }

    public void setShipMentions(List<Ship> shipMentions) {
        this.shipMentions = shipMentions;
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
