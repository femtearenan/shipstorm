package se.femtearenan.shipstorm.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Source extends BaseEntity{

    String source;
    String name;

    @OneToMany
    List<Scrap> scraps;
}
