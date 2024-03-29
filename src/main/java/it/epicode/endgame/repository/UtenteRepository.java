package it.epicode.endgame.repository;

import it.epicode.endgame.model.Utente;
import it.epicode.endgame.model.Videogioco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer> {
    Optional<Utente> findByUsername(String username);
    @Query("SELECT v FROM Utente u JOIN u.preferiti v WHERE u.id = :idUtente")
    Page<Videogioco> findPreferitiById(@Param("idUtente") int idUtente, Pageable pageable);
    List<Utente> findAllByOrderByTipologiaAsc();
    Optional<Utente> findById(int id);

}
