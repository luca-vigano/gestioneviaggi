package lucavigano.organizzaviaggi.services;

import com.cloudinary.Cloudinary;
import lucavigano.organizzaviaggi.entities.Dipendente;
import lucavigano.organizzaviaggi.repository.DipenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DipendenteService {

    @Autowired
    private DipenteRepository dipenteRepository;
    @Autowired
    private Cloudinary cloudinaryUploader;


}
