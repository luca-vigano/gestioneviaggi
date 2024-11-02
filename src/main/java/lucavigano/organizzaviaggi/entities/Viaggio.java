package lucavigano.organizzaviaggi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "viaggi")
@Getter
@Setter
@ToString
@NoArgsConstructor

public class Viaggio {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String destinazione;
    private LocalDate data_viaggio;

    private String stato;


    public Viaggio(String destinazione, LocalDate data_viaggio) {
        this.destinazione = destinazione;
        this.data_viaggio = data_viaggio;
        this.stato = "in programma";
    }
}
