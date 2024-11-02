package lucavigano.organizzaviaggi.payloads;

import jakarta.validation.constraints.NotEmpty;
import lucavigano.organizzaviaggi.entities.Dipendente;
import lucavigano.organizzaviaggi.entities.Viaggio;

import java.util.UUID;

public record PrenotazioneDTO(
        @NotEmpty(message = "l' Id del viaggio è obbligatorio!")
        UUID viaggioId,
        @NotEmpty(message = "l'Id del dipendente è obbligatorio!")
        UUID dipendenteId,
        String note) {
}
