package se.femtearenan.shipstorm.model;

import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
public class Source extends BaseEntity{

    String source;
    String name;

    //ManyToMany
    ArrayList<Scrap> scraps;
}
