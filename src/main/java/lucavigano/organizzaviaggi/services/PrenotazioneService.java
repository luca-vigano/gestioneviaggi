package lucavigano.organizzaviaggi.services;


import lucavigano.organizzaviaggi.entities.Dipendente;
import lucavigano.organizzaviaggi.entities.Prenotazione;
import lucavigano.organizzaviaggi.entities.Viaggio;
import lucavigano.organizzaviaggi.exception.NotFoundException;
import lucavigano.organizzaviaggi.payloads.PrenotazioneDTO;
import lucavigano.organizzaviaggi.repository.PrenotazioneRepository;
import lucavigano.organizzaviaggi.repository.DipenteRepository;
import lucavigano.organizzaviaggi.repository.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;
    @Autowired
    private DipenteRepository dipendenteRepository;
    @Autowired
    private ViaggioRepository viaggioRepository;

    public Prenotazione save(PrenotazioneDTO body){
        Dipendente dipendente = dipendenteRepository.findById(body.dipendenteId())
                .orElseThrow(() -> new NotFoundException(body.dipendenteId()));

        Viaggio viaggio = viaggioRepository.findById(body.viaggioId())
                .orElseThrow(() -> new NotFoundException(body.viaggioId()));

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setDipendente(dipendente);
        prenotazione.setViaggio(viaggio);
        prenotazione.setNote(body.note());

        return prenotazioneRepository.save(prenotazione);
    }

    public Page<Prenotazione> findAll(int page, int size, String sortBy){
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.prenotazioneRepository.findAll(pageable);
    }

    public Prenotazione findById(UUID prenotazioneId){
        return this.prenotazioneRepository.findById(prenotazioneId).orElseThrow(() -> new NotFoundException(prenotazioneId));
    }

    public Prenotazione findByIdAndUpdate(UUID prenotazioneId, PrenotazioneDTO prenotazioneDTO) {
        Prenotazione prenotazione = prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(() -> new NotFoundException(prenotazioneId));

        if (prenotazioneDTO.dipendenteId() != null) {
            Dipendente dipendente = dipendenteRepository.findById(prenotazioneDTO.dipendenteId())
                    .orElseThrow(() -> new NotFoundException(prenotazioneId));
            prenotazione.setDipendente(dipendente);
        }

        if (prenotazioneDTO.viaggioId() != null) {
            Viaggio viaggio = viaggioRepository.findById(prenotazioneDTO.viaggioId())
                    .orElseThrow(() -> new NotFoundException(prenotazioneId));
            prenotazione.setViaggio(viaggio);
        }

        if (prenotazioneDTO.note() != null) {
            prenotazione.setNote(prenotazioneDTO.note());
        }

        return prenotazioneRepository.save(prenotazione);
    }

    public void findByIdAndDelete (UUID prenotazioneId){
        Prenotazione viaggioFound = this.findById(prenotazioneId);
        this.prenotazioneRepository.delete(viaggioFound);
    }
}
