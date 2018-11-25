package se.femtearenan.shipstorm.model;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
