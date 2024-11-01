package lucavigano.organizzaviaggi.payloads;

import jakarta.validation.constraints.NotEmpty;
import lucavigano.organizzaviaggi.entities.Dipendente;
import lucavigano.organizzaviaggi.entities.Viaggio;

public record PrenotazioneDTO(
        @NotEmpty(message = "Il viaggio è obbligatorio!")
        Viaggio viaggio,
        @NotEmpty(message = "Il dipendente è obbligatorio!")
        Dipendente dipendente,
        String note) {
}
