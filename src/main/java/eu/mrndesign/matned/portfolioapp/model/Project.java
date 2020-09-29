package eu.mrndesign.matned.portfolioapp.model;

import eu.mrndesign.matned.portfolioapp.dto.ProjectDTO;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static eu.mrndesign.matned.portfolioapp.statics.Patterns.DATE_TIME_FORMATTER_ONLY_DATE;

@Entity
public class Project extends BaseEntity{

    private String projectTitle;
    private String projectUrl;
    private String projectDescription;
    private LocalDate projectReleaseDate;

    public Project() {
    }

    public Project(String projectTitle,
                   String projectUrl,
                   String projectDescription,
                   LocalDate projectReleaseDate) {
        this.projectTitle = projectTitle;
        this.projectUrl = projectUrl;
        this.projectDescription = projectDescription;
        this.projectReleaseDate = projectReleaseDate;
        creationDate = LocalDateTime.now();
    }

    public static Project apply(ProjectDTO dto){
        return new Project(
                dto.getProjectTitle(),
                dto.getProjectUrl(),
                dto.getProjectDescription(),
                dto.getProjectReleaseDate() != null ?
                        LocalDate.parse(dto.getProjectReleaseDate(), DATE_TIME_FORMATTER_ONLY_DATE): null );
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public LocalDate getProjectReleaseDate() {
        return projectReleaseDate;
    }

    public void setProjectReleaseDate(LocalDate projectReleaseDate) {
        this.projectReleaseDate = projectReleaseDate;
    }
}
