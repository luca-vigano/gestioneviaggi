package lucavigano.organizzaviaggi.payloads;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lucavigano.organizzaviaggi.entities.Dipendente;

import java.time.LocalDate;

public record ViaggioDTO(

        @NotEmpty(message = "La destinazione è obbligatoria!")
        @Size(min = 2, max = 40, message = "La destinazione deve essere compreso tra 2 e 40 caratteri!")
        String destinazione,
        @NotEmpty(message = "La data del viaggio è obbligatoria!")
        LocalDate data_viaggio,
        @NotEmpty(message = "Lo stato del viaggio è obbligatorio!")
        @Size(min = 2, max = 40, message = "Lo stato del deve essere compreso tra 2 e 40 caratteri!")
        String stato,
        @NotEmpty(message = "Il dipendente è obbligatorio!")
        Dipendente dipendente) {
}
