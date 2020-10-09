package eu.mrndesign.matned.portfolioapp.dto;

import eu.mrndesign.matned.portfolioapp.model.ProjectImage;
import eu.mrndesign.matned.portfolioapp.statics.Patterns;

import java.time.LocalDateTime;

public class ProjectImageDTO{

    private String title;
    private String imageUrl;
    private Long projectId;

    protected Long id;
    protected Long version;
    protected String creationDate;
    protected String updateDate;

    public ProjectImageDTO() {
    }

    public ProjectImageDTO(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }


    public static ProjectImageDTO apply(ProjectImage projectImage) {
        if (projectImage != null) {
            ProjectImageDTO projectImageDTO = new ProjectImageDTO(projectImage.getTitle(), projectImage.getImageUrl());
            projectImageDTO.setTitle(projectImage.getTitle());
            projectImageDTO.setId(projectImage.getId());
            projectImageDTO.setVersion(projectImage.getVersion());
            projectImageDTO.setCreationDate(projectImage.getCreationDate().format(Patterns.DATE_TIME_FORMATTER_TASK));
            if (projectImage.getUpdateDate() != null) projectImageDTO.setUpdateDate(projectImage.getUpdateDate().format(Patterns.DATE_TIME_FORMATTER_TASK));
            return projectImageDTO;
        } else return null;
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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
