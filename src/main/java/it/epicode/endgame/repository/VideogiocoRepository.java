package it.epicode.endgame.repository;

import it.epicode.endgame.model.Utente;
import it.epicode.endgame.model.Videogioco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideogiocoRepository extends JpaRepository<Videogioco, Integer>, PagingAndSortingRepository<Videogioco, Integer> {
    Page<Videogioco> findByUtente(Utente utente, Pageable pageable);
}
