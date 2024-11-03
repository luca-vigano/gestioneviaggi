package lucavigano.organizzaviaggi.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lucavigano.organizzaviaggi.entities.Dipendente;
import lucavigano.organizzaviaggi.exception.BadRequestException;
import lucavigano.organizzaviaggi.exception.NotFoundException;
import lucavigano.organizzaviaggi.payloads.DipendenteDTO;
import lucavigano.organizzaviaggi.repository.DipenteRepository;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class DipendenteService {

    @Autowired
    private DipenteRepository dipendenteRepository;
    @Autowired
    private Cloudinary cloudinaryUploader;

    public Dipendente save(DipendenteDTO body) {
        this.dipendenteRepository.findByEmail(body.email()).ifPresent(dipendente -> {
                    throw new BadRequestException("Email " + body.email() + " già in uso");
                }
        );
        Dipendente newDipendente = new Dipendente(body.username(), body.nome(), body.cognome(), body.email());
        newDipendente.setAvatar("https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());
        return this.dipendenteRepository.save(newDipendente);
    }


    public Page<Dipendente> findAll(int page, int size, String sortBy){
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.dipendenteRepository.findAll(pageable);
    }

    public Dipendente findById(UUID dipendenteId) {
        return this.dipendenteRepository.findById(dipendenteId).orElseThrow(() -> new NotFoundException(dipendenteId));
    }

    public Dipendente findByIdAndUpdate(UUID dipendenteId, DipendenteDTO body) {
        Dipendente dipendenteFound = this.findById(dipendenteId);

        if (!dipendenteFound.getEmail().equals(body.email())) {
            this.dipendenteRepository.findByEmail(body.email()).ifPresent(
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
        return this.dipendenteRepository.save(dipendenteFound);
    }

    public void findByIdAndDelete(UUID dipendenteId) {
        Dipendente dipendenteFound = this.findById(dipendenteId);
        this.dipendenteRepository.delete(dipendenteFound);
    }

    public Dipendente uploadAvatar (UUID dipendenteId, MultipartFile file){

        if (file.isEmpty()) {
            throw new BadRequestException("Il file dell'immagine non può essere vuoto");
        }

        String url = null;
        try {
            url = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        } catch (IOException e ) {
            throw new BadRequestException("errore nel caricamento dell'immagine");
        }

        Dipendente dipendenteFound = this.findById(dipendenteId);
        if (dipendenteFound == null) {
            throw new BadRequestException("Dipendente non trovato con l'ID fornito");
        }

        dipendenteFound.setAvatar(url);

        return this.dipendenteRepository.save(dipendenteFound);
    }
}
