package se.femtearenan.shipstorm.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Source {

    String source;
    String name;

    @OneToMany
    List<Scrap> scraps;

    public Source() {
    }

    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
