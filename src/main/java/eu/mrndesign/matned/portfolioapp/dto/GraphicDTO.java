package eu.mrndesign.matned.portfolioapp.dto;

import eu.mrndesign.matned.portfolioapp.model.Graphic;
import eu.mrndesign.matned.portfolioapp.validation.DateMatchesPattern;
import eu.mrndesign.matned.portfolioapp.validation.IsNumericBigDecimal;
import eu.mrndesign.matned.portfolioapp.validation.IsNumericInteger;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static eu.mrndesign.matned.portfolioapp.statics.Patterns.DATE_TIME_FORMATTER_ONLY_DATE;
import static eu.mrndesign.matned.portfolioapp.statics.Patterns.DATE_TIME_FORMATTER_TASK;

public class GraphicDTO {

    private Long id;
    private String imageUrl;
    @NotEmpty(message = "This field cannot be empty")
    @NotBlank(message = "This field cannot be empty")
    @NotNull(message = "This field cannot be empty")
    private String title;
    @NotEmpty(message = "This field cannot be empty")
    @NotBlank(message = "This field cannot be empty")
    @NotNull(message = "This field cannot be empty")
    @Size(min = 10, max = 5000, message = "The description should be between {min} and {max} signs.")
    private String description;
    @DateMatchesPattern
    private String dateOfPublication;
    @IsNumericInteger
    private Integer numberOfCopies;
    private Integer copiesMade;
    private String series;
    private String dateOfCreation;
    private String dateOfUpdate;

    public GraphicDTO() {
    }

    private GraphicDTO(GraphicDTOBuilder builder) {
        this.id = builder.id;
        this.imageUrl = builder.imageUrl;
        this.title = builder.title;
        this.description = builder.description;
        this.dateOfPublication = builder.dateOfPublication;
        this.numberOfCopies = builder.numberOfCopies;
        this.copiesMade = builder.copiesMade;
        this.series = builder.series;
        this.dateOfCreation = builder.dateOfCreation;
        this.dateOfUpdate = builder.dateOfUpdate;
    }

    public static GraphicDTO apply(Graphic entity){
        return new GraphicDTOBuilder()
                .id(entity.getId())
                .title(entity.getTitle())
                .imageUrl(entity.getImageUrl())
                .description(entity.getDescription())
                .dateOfPublication(entity.getDateOfPublication() != null ? entity.getDateOfPublication().format(DATE_TIME_FORMATTER_ONLY_DATE) : null)
                .numberOfCopies(entity.getNumberOfCopies())
                .copiesMade(entity.getCopiesMade())
                .series(entity.getSerie() != null ? entity.getSerie().getSetName() : null)
                .dateOfCreation(entity.getCreationDate() != null ? entity.getCreationDate().format(DATE_TIME_FORMATTER_TASK) : null)
                .dateOfUpdate(entity.getUpdateDate() != null ? entity.getUpdateDate().format(DATE_TIME_FORMATTER_TASK) : null)
                .build();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(String dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public Integer getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(Integer numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public Integer getCopiesMade() {
        return copiesMade;
    }

    public void setCopiesMade(Integer copiesMade) {
        this.copiesMade = copiesMade;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getDateOfUpdate() {
        return dateOfUpdate;
    }

    public void setDateOfUpdate(String dateOfUpdate) {
        this.dateOfUpdate = dateOfUpdate;
    }

    public static class GraphicDTOBuilder {

        private Long id;
        private String imageUrl;
        private String title;
        private String description;
        private String dateOfPublication;
        private Integer numberOfCopies;
        private Integer copiesMade;
        private String series;
        private String dateOfCreation;
        private String dateOfUpdate;

        public GraphicDTOBuilder(){
        }

        public GraphicDTOBuilder id(Long id){
            this.id = id;
            return this;
        }

        public GraphicDTOBuilder imageUrl(String imageUrl){
            this.imageUrl = imageUrl;
            return this;
        }

        public GraphicDTOBuilder title(String title){
            this.title = title;
            return this;
        }

        public GraphicDTOBuilder description(String description){
            this.description = description;
            return this;
        }

        public GraphicDTOBuilder dateOfPublication(String dateOfPublication){
            this.dateOfPublication = dateOfPublication;
            return this;
        }

        public GraphicDTOBuilder numberOfCopies(Integer numberOfCopies){
            this.numberOfCopies = numberOfCopies;
            return this;
        }

        public GraphicDTOBuilder copiesMade(Integer copiesMade){
            this.copiesMade = copiesMade;
            return this;
        }

        public GraphicDTOBuilder series(String series){
            this.series = series;
            return this;
        }

        public GraphicDTOBuilder dateOfCreation(String dateOfCreation){
            this.dateOfCreation = dateOfCreation;
            return this;
        }

        public GraphicDTOBuilder dateOfUpdate(String dateOfUpdate){
            this.dateOfUpdate = dateOfUpdate;
            return this;
        }

        public GraphicDTO build(){
            return new GraphicDTO(this);
        }


    }
}
