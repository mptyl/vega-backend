package it.tylconsulting.vega.vegabackend.service;

import it.tylconsulting.vega.vegamodel.db.model.GruppoAziende;
import it.tylconsulting.vega.vegamodel.db.repository.GruppoAziendeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
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
            return (List<GruppoAziende>) gruppoAziendeRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
        else
            return gruppoAziendeRepository.search(stringFilter);
    }

    public long countGruppiAziende(){
        return gruppoAziendeRepository.count();
    }

    public void deleteGruppoAziende(GruppoAziende gruppoAziende) {
        gruppoAziendeRepository.delete(gruppoAziende);
    }

    public void saveGruppoAziende(GruppoAziende gruppoAziende) {
        if (gruppoAziende == null) {
            System.err.println("GruppoAziende is null. Are you sure you have connected your form to the application?");
            return;
        }
        gruppoAziendeRepository.save(gruppoAziende);
    }



}
