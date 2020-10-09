package eu.mrndesign.matned.portfolioapp.model;

import eu.mrndesign.matned.portfolioapp.dto.ProjectDTO;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static eu.mrndesign.matned.portfolioapp.statics.Patterns.DATE_TIME_FORMATTER_ONLY_DATE;

@Entity
public class Project extends BaseEntity{

    private String projectTitle;
    private String projectUrl;
    private String projectDescription;
    private LocalDate projectReleaseDate;

    @OneToMany(mappedBy = "project")
    private List<ProjectImage> images;

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
        this.creationDate = LocalDateTime.now();
        this.images = new LinkedList<>();    }

    public static Project apply(ProjectDTO dto){
        return new Project(
                dto.getProjectTitle(),
                dto.getProjectUrl(),
                dto.getProjectDescription(),
                dto.getProjectReleaseDate().equals("") ?
                        null : LocalDate.parse(dto.getProjectReleaseDate(), DATE_TIME_FORMATTER_ONLY_DATE) );

    }

    public List<ProjectImage> getImages() {
        return images;
    }

    public void setImages(List<ProjectImage> images) {
        this.images = images;
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
