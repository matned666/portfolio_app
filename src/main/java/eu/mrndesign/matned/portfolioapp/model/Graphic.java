package eu.mrndesign.matned.portfolioapp.model;


import eu.mrndesign.matned.portfolioapp.dto.GraphicDTO;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static eu.mrndesign.matned.portfolioapp.statics.Patterns.DATE_TIME_FORMATTER_ONLY_DATE;
import static eu.mrndesign.matned.portfolioapp.statics.Patterns.DATE_TIME_FORMATTER_TASK;

@Entity
public class Graphic extends BaseEntity{

    private String imageUrl;
    private String title;
    private String description;
    private LocalDate dateOfPublication;
    private Integer numberOfCopies;
    private Integer copiesMade;

    @ManyToOne
    private GraphicSet serie;

    public Graphic() {
    }



    public Graphic(String imageUrl, String title) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.creationDate = LocalDateTime.now();
    }

    public static Graphic apply(GraphicDTO dto, GraphicSet set){
        Graphic graphic = new Graphic(dto.getImageUrl(), dto.getTitle());
        graphic.setSerie(set);
        graphic.setNumberOfCopies(dto.getNumberOfCopies());
        graphic.setDescription(dto.getDescription());
        graphic.setCopiesMade(dto.getCopiesMade());
        if (dto.getDateOfPublication() != null ) graphic.setDateOfPublication(LocalDate.parse(dto.getDateOfPublication(), DATE_TIME_FORMATTER_ONLY_DATE));
        if (dto.getDateOfCreation() != null ) graphic.setCreationDate(LocalDateTime.parse(dto.getDateOfCreation(), DATE_TIME_FORMATTER_TASK));
        if (dto.getDateOfUpdate() != null ) graphic.setUpdateDate(LocalDateTime.parse(dto.getDateOfUpdate(), DATE_TIME_FORMATTER_TASK));
        graphic.setId(dto.getId());
        return graphic;
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

    public LocalDate getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(LocalDate dateOfPublication) {
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

    public GraphicSet getSerie() {
        return serie;
    }

    public void setSerie(GraphicSet serie) {
        this.serie = serie;
    }
}
