package it.epicode.endgame.repository;

import it.epicode.endgame.model.Videogioco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VideogiocoRepository extends JpaRepository<Videogioco, Integer>, PagingAndSortingRepository<Videogioco, Integer> {
}
