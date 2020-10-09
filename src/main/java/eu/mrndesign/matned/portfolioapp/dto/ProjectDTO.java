package eu.mrndesign.matned.portfolioapp.dto;

import eu.mrndesign.matned.portfolioapp.model.Project;

import static eu.mrndesign.matned.portfolioapp.statics.Patterns.*;

public class ProjectDTO {

    private String projectTitle;
    private String projectUrl;
    private String projectDescription;
    private String projectReleaseDate;
    private String image;
    protected Long id;
    protected String creationDate;
    protected String updateDate;


    public ProjectDTO() {
    }

    private ProjectDTO(ProjectDTOBuilder builder){
        this.projectTitle = builder.projectTitle;
        this.projectUrl = builder.projectUrl;
        this.projectDescription = builder.projectDescription;
        this.projectReleaseDate = builder.projectReleaseDate;
        this.id = builder.id;
        this.creationDate = builder.creationDate;
        this.updateDate = builder.updateDate;
        this.image = builder.image;
    }

    public static ProjectDTO apply(Project entity){
        return new ProjectDTOBuilder(
                entity.getProjectTitle(),
                entity.getProjectUrl(),
                entity.getProjectDescription(),
                entity.getProjectReleaseDate() != null ? entity.getProjectReleaseDate().format(DATE_TIME_FORMATTER_ONLY_DATE) : null)
                .id(entity.getId())
                .creationDate(entity.getCreationDate() != null ? entity.getCreationDate().format(DATE_TIME_FORMATTER_TASK): null)
                .updateDate(entity.getUpdateDate() != null ? entity.getUpdateDate().format(DATE_TIME_FORMATTER_TASK): null)
                .image(!entity.getImages().isEmpty() ? entity.getImages().get(0).getImageUrl() : null)
                .build();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getProjectReleaseDate() {
        return projectReleaseDate;
    }

    public void setProjectReleaseDate(String projectReleaseDate) {
        this.projectReleaseDate = projectReleaseDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public static class ProjectDTOBuilder{

        private String projectTitle;
        private String projectUrl;
        private String projectDescription;
        private String projectReleaseDate;
        private String image;
        protected Long id;
        protected String creationDate;
        protected String updateDate;

        public ProjectDTOBuilder(String projectTitle,
                                 String projectUrl,
                                 String projectDescription,
                                 String projectReleaseDate){
            this.projectTitle = projectTitle;
            this.projectUrl = projectUrl;
            this.projectDescription = projectDescription;
            this.projectReleaseDate = projectReleaseDate;
        }

        public ProjectDTOBuilder id(Long id){
            this.id = id;
            return this;
        }

        public ProjectDTOBuilder creationDate(String creationDate){
            this.creationDate = creationDate;
            return this;
        }

        public ProjectDTOBuilder updateDate(String updateDate){
            this.updateDate = updateDate;
            return this;
        }

        public ProjectDTOBuilder image(String image){
            this.image = image;
            return this;
        }

        public ProjectDTO build(){
            return new ProjectDTO(this);
        }



    }
}
