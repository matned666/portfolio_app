package eu.mrndesign.matned.portfolioapp.repository;

import eu.mrndesign.matned.portfolioapp.model.GraphicSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GraphicSetRepository extends JpaRepository<GraphicSet, Long> {

    @Query("select case when count(gs)> 0 then true else false end from GraphicSet gs where lower(gs.setName) like lower(?1)")
    boolean existsBySetName(String setName);

    @Query("select gs from GraphicSet gs where lower(gs.setName) = lower(?1)")
    Optional<GraphicSet> findBySetName(String setName);
}