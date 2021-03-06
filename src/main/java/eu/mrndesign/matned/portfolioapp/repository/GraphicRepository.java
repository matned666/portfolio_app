package eu.mrndesign.matned.portfolioapp.repository;

import eu.mrndesign.matned.portfolioapp.model.Graphic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

public interface GraphicRepository extends JpaRepository<Graphic, Long> {

    @Query("select case when count(g)> 0 then true else false end from Graphic g where lower(g.imageUrl) like lower(?1)")
    boolean existsByImageUrl(String imageUrl);

    @Query("select g from Graphic g where lower(g.description) like lower(?1) or lower(g.title) like lower(?1)")
    List<Graphic> findAll(String str);
}
