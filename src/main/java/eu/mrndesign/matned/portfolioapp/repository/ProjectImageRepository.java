package eu.mrndesign.matned.portfolioapp.repository;

import eu.mrndesign.matned.portfolioapp.model.ProjectImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectImageRepository extends JpaRepository<ProjectImage, Long> {

    @Query("select pi from ProjectImage pi where pi.project.id = ?1")
    List<ProjectImage> findByProjectId(Long projectId);

    @Query("select case when count(pi) > 0 then true else false end from ProjectImage pi where lower(pi.imageUrl) like lower(?1)")
    boolean existsByImageUrl(String imageUrl);

}
