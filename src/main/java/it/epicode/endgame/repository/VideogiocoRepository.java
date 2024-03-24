package it.epicode.endgame.repository;

import it.epicode.endgame.model.Utente;
import it.epicode.endgame.model.Videogioco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideogiocoRepository extends JpaRepository<Videogioco, Integer>, PagingAndSortingRepository<Videogioco, Integer> {
    Page<Videogioco> findByUtente(Utente utente, Pageable pageable);
    @Query("SELECT v FROM Videogioco v ORDER BY v.titolo ASC")
    Page<Videogioco> findAllByOrderByTitoloAsc(Pageable pageable);
    @Query("SELECT v FROM Videogioco v WHERE v.titolo ILIKE concat('%', :titolo, '%') ORDER BY v.titolo ASC")
    Page<Videogioco> findByTitoloContainingIgnoreCase(String titolo, Pageable pageable);
    Page<Videogioco> findByAnnoDiUscita(int annoDiUscita, Pageable pageable);
    @Query("SELECT v FROM Videogioco v WHERE v.genere ILIKE concat('%', :genere, '%')")
    Page<Videogioco> findByGenere(String genere, Pageable pageable);
    @Query("SELECT v FROM Videogioco v WHERE lower(v.console) LIKE lower(concat('%', :console, '%'))")
    Page<Videogioco> findByConsole(String console, Pageable pageable);
    @Query("SELECT v FROM Videogioco v WHERE v.metascore >= :metascore ORDER BY v.metascore DESC")
    Page<Videogioco> findByBestMetascore(int metascore, Pageable pageable);
}
