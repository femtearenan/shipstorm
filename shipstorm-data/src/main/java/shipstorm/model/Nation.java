package shipstorm.model;


import java.util.ArrayList;

public class Nation extends BaseEntity{

    String name;
    String abbreviation;

    //OneToMany(mappedBy = "nation")
    ArrayList<Ship> ships;

    public Nation(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }
}
