package se.femtearenan.shipstorm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.femtearenan.shipstorm.model.Nation;
import se.femtearenan.shipstorm.repositories.NationRepository;

@Service
public class NationServiceImpl implements NationService {
    private NationRepository nationRepository;

    @Autowired
    public void setNationRepository(NationRepository nationRepository) {
        this.nationRepository = nationRepository;
    }

    @Override
    public Iterable<Nation> listAllNations() {
        return nationRepository.findAll();
    }

    @Override
    public Nation getNationById(Long id) {
        return nationRepository.findById(id).orElse(null);
    }

    @Override
    public Nation save(Nation nation) {
        return nationRepository.save(nation) ;
    }
}
