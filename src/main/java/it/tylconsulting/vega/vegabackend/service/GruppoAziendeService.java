package it.tylconsulting.vega.vegabackend.service;

import it.tylconsulting.vega.vegamodel.db.model.GruppoAziende;
import it.tylconsulting.vega.vegamodel.db.repository.GruppoAziendeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GruppoAziendeService {
    private final GruppoAziendeRepository gruppoAziendeRepository;

    private GruppoAziendeService(GruppoAziendeRepository gruppoAziendeRepository){
        this.gruppoAziendeRepository=gruppoAziendeRepository;
    }

    public List<GruppoAziende> findAllGruppiziende(String stringFilter){
        if (StringUtils.isEmpty(stringFilter))
            return (List<GruppoAziende>) gruppoAziendeRepository.findAll();
        else
            return gruppoAziendeRepository.search(stringFilter);
    }

    public long countGruppiAziende(){
        return gruppoAziendeRepository.count();
    }
}