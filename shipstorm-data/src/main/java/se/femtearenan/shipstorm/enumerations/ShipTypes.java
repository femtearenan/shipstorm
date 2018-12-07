package se.femtearenan.shipstorm.enumerations;

// For reference see https://en.wikipedia.org/wiki/Hull_classification_symbol [2018-12-06]
public enum ShipTypes {
    A ("Auxiliary"),
    AM ("Mine Sweeper"),
    CC ("Cruiser"),
    CG ("Coast Guard"),
    CV ("Aircraft carrier"),
    CVH ("Helicopter carrier"),
    DD ("Destroyer"),
    FF ("Frigate"),
    K ("Corvette"),
    LC ("Landing Craft"),
    MCM ("Mine Countermeasure"),
    MH ("Mine Hunter"),
    PB ("Patrol boat"),
    SS ("Submarine");

    private final String typeDescription;

    public String getTypeDescription() {
        return typeDescription;
    }

    ShipTypes(String typeDescription) {
        this.typeDescription = typeDescription;
    }
}
