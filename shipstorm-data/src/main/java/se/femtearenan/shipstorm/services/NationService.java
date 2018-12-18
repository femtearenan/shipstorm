package se.femtearenan.shipstorm.services;

import se.femtearenan.shipstorm.model.Nation;

import java.util.List;

public interface NationService {
    Iterable<Nation> listAllNations();

    Nation getNationById(Long id);

    Nation save(Nation nation);

    List<Nation> findByName(String name);

    List<Nation> findByNameContaining(String name);
}
