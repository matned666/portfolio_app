package eu.mrndesign.matned.portfolioapp.model;

import eu.mrndesign.matned.portfolioapp.dto.ProjectImageDTO;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class ProjectImage extends BaseImageEntity{

    @ManyToOne
    private Project project;

    public ProjectImage() {
    }

    public ProjectImage(String title, String imageUrl, Project project) {
        super.title = title;
        this.imageUrl = imageUrl;
        this.project = project;
        this.creationDate = LocalDateTime.now();
    }

    public static ProjectImage apply(ProjectImageDTO dto, Project project){
        ProjectImage entity = new ProjectImage(dto.getTitle(), dto.getImageUrl(), project);
        return (ProjectImage) getImageBaseData(dto, entity);

    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "ProjectImage{" +
                "project=" + project +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", id=" + id +
                ", version=" + version +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
