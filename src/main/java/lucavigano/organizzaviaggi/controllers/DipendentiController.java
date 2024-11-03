package lucavigano.organizzaviaggi.controllers;

import lucavigano.organizzaviaggi.entities.Dipendente;
import lucavigano.organizzaviaggi.exception.BadRequestException;
import lucavigano.organizzaviaggi.payloads.DipendenteDTO;
import lucavigano.organizzaviaggi.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dipendenti")
public class DipendentiController {

    @Autowired
    private DipendenteService dipendenteService;

    @GetMapping
    public Page<Dipendente> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "id") String sortBy) {
        return this.dipendenteService.findAll(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente save(@RequestBody @Validated DipendenteDTO body, BindingResult validationResult) {
        // @Validated serve per "attivare" le regole di validazione descritte nel DTO
        // BindingResult contiene l'esito della validazione, quindi sarà utile per capire se ci sono stati errori e quali essi siano
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }

        return this.dipendenteService.save(body);
    }

    @GetMapping("/{dipendenteId}")
    public Dipendente findById(@PathVariable UUID dipendenteId) {
        return this.dipendenteService.findById(dipendenteId);
    }

    @PutMapping("/{dipendenteId}")
    public Dipendente findByIdAndUpdate(@PathVariable UUID dipendenteId, @RequestBody @Validated DipendenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Errore nel payload!");
        }
        return this.dipendenteService.findByIdAndUpdate(dipendenteId, body);
    }

    @DeleteMapping("/{dipendenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID dipendenteId) {
        this.dipendenteService.findByIdAndDelete(dipendenteId);
    }

    @PatchMapping("/{dipendenteId}/avatar")
    public Dipendente uploadAvatar(@PathVariable UUID dipendenteId, @RequestParam("avatar")MultipartFile file){
        return this.dipendenteService.uploadAvatar(dipendenteId,file);
    }
}
