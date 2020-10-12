package eu.mrndesign.matned.portfolioapp.model;

import eu.mrndesign.matned.portfolioapp.dto.ProjectImageDTO;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

import static eu.mrndesign.matned.portfolioapp.statics.Patterns.DATE_TIME_FORMATTER_TASK;

@MappedSuperclass
public class BaseImageEntity extends BaseEntity{

    protected String title;
    protected String imageUrl;

    protected static BaseImageEntity getImageBaseData(ProjectImageDTO dto, BaseImageEntity entity) {
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

    @Override
    public String toString() {
        return "BaseImageEntity{" +
                "title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
