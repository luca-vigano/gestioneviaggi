package lucavigano.organizzaviaggi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "prenotazione")
@Getter
@Setter
@NoArgsConstructor
@ToString

public class Prenotazione {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private LocalDate dataRichiesta;
    private String note;

    @ManyToOne
    private Dipendente dipendente;

    @ManyToOne
    private Viaggio viaggio;

    public Prenotazione(Viaggio viaggio, Dipendente dipendente, String note) {
        this.dataRichiesta = LocalDate.now();
        this.viaggio = viaggio;
        this.dipendente = dipendente;
        this.note = note;
    }
}
