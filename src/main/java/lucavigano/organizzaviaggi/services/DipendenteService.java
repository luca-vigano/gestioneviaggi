package lucavigano.organizzaviaggi.services;

import com.cloudinary.Cloudinary;
import lucavigano.organizzaviaggi.entities.Dipendente;
import lucavigano.organizzaviaggi.exception.BadRequestException;
import lucavigano.organizzaviaggi.exception.NotFoundException;
import lucavigano.organizzaviaggi.payloads.DipendenteDTO;
import lucavigano.organizzaviaggi.repository.DipenteRepository;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DipendenteService {

    @Autowired
    private DipenteRepository dipenteRepository;
    @Autowired
    private Cloudinary cloudinaryUploader;

    public Dipendente save(DipendenteDTO body) {
        this.dipenteRepository.findByEmail(body.email()).ifPresent(dipendente -> {
                    throw new BadRequestException("Email " + body.email() + " già in uso");
                }
        );
        Dipendente newDipendente = new Dipendente(body.username(), body.nome(), body.cognome(), body.email());
        newDipendente.setAvatar("https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());
        return this.dipenteRepository.save(newDipendente);
    }

//    public Page<Dipendente> findAll(int page, int size, String sortBy){
//        if (size > 100) size = 100; // Limitiamo la size max a 100 così da client non possono inserire numeri troppo grandi
//        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
//        // Pageable ci consente di configurare come devono essere paginati i risultati passando numero di pagina, numero elementi pagina e criterio di ordinamento
//        return this.dipenteRepository.findAll(pageable);
//    }

    public Dipendente findById(UUID dipendenteId) {
        return this.dipenteRepository.findById(dipendenteId).orElseThrow(() -> new NotFoundException(dipendenteId));
    }

    public Dipendente findByIdAndUpdate(UUID dipendenteId, DipendenteDTO body) {
        Dipendente dipendenteFound = this.findById(dipendenteId);

        if (!dipendenteFound.getEmail().equals(body.email())) {
            this.dipenteRepository.findByEmail(body.email()).ifPresent(
                    dipendente -> {
                        throw new BadRequestException("Email " + body.email() + " già in uso!");
                    }
            );
        }

        dipendenteFound.setUsername(body.username());
        dipendenteFound.setNome(body.nome());
        dipendenteFound.setCognome(body.cognome());
        dipendenteFound.setEmail(body.email());
        dipendenteFound.setAvatar("https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());
        return this.dipenteRepository.save(dipendenteFound);
    }

    public void findByIdAndDelete(UUID dipendenteId) {
        Dipendente dipendenteFound = this.findById(dipendenteId);
        this.dipenteRepository.delete(dipendenteFound);
    }
}
