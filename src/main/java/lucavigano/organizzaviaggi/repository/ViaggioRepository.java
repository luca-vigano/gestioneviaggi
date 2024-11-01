package lucavigano.organizzaviaggi.repository;

import lucavigano.organizzaviaggi.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ViaggioRepository extends JpaRepository<Viaggio, UUID> {
}
