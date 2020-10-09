package eu.mrndesign.matned.portfolioapp.model;

import eu.mrndesign.matned.portfolioapp.dto.ProjectImageDTO;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

import static eu.mrndesign.matned.portfolioapp.statics.Patterns.DATE_TIME_FORMATTER_TASK;

@Entity
public class ProjectImage extends BaseEntity{

    private String title;
    private String imageUrl;

    @ManyToOne
    private Project project;

    public ProjectImage() {
    }

    public ProjectImage(String title, String imageUrl, Project project) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.project = project;
        this.creationDate = LocalDateTime.now();
    }

    public static ProjectImage apply(ProjectImageDTO dto, Project project){
        ProjectImage entity = new ProjectImage(dto.getTitle(), dto.getImageUrl(), project);
        if (dto.getCreationDate() != null)
            entity.setCreationDate(LocalDateTime.parse(dto.getCreationDate(), DATE_TIME_FORMATTER_TASK));
        if (dto.getUpdateDate() != null)
            entity.setUpdateDate(LocalDateTime.parse(dto.getUpdateDate(), DATE_TIME_FORMATTER_TASK));
        entity.setId(dto.getId());
        entity.setVersion(dto.getVersion());
        return entity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
