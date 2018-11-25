package se.femtearenan.shipstorm.services;

import se.femtearenan.shipstorm.model.Nation;

public interface NationService {
    Iterable<Nation> listAllNations();

    Nation getNationById(Long id);

    Nation save(Nation nation);
}
