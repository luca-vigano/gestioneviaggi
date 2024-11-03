package lucavigano.organizzaviaggi.repository;

import lucavigano.organizzaviaggi.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {
    List<Prenotazione> findByDipendente_EmailAndViaggio_Dataviaggio(String username, LocalDate data);
}
