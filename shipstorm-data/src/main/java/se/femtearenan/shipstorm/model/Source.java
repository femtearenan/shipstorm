package se.femtearenan.shipstorm.model;

import java.util.ArrayList;

public class Source extends BaseEntity{

    String source;
    String name;

    //ManyToMany
    ArrayList<Scrap> scraps;
}
