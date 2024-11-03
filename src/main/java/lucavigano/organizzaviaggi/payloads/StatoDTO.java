package lucavigano.organizzaviaggi.payloads;

import jakarta.validation.constraints.NotEmpty;

public record StatoDTO(
        @NotEmpty(message = "nuovo stato obbligatorio!!")
        String stato
) {
}
