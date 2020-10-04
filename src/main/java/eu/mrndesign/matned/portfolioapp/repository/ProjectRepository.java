package eu.mrndesign.matned.portfolioapp.repository;

import eu.mrndesign.matned.portfolioapp.dto.ProjectDTO;
import eu.mrndesign.matned.portfolioapp.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("select case when count(p)> 0 then true else false end from Project p where lower(p.projectUrl) like lower(?1)")
    boolean existByLink(String link);

    @Query("select p from Project p where lower(p.projectDescription) like lower(?1) or lower(p.projectTitle) like lower(?1)")
    List<Project> findAll(String searched);

}
