package eu.mrndesign.matned.portfolioapp.model;

import eu.mrndesign.matned.portfolioapp.dto.ShopCategoryDTO;

import javax.persistence.Entity;
import java.time.LocalDateTime;

import static eu.mrndesign.matned.portfolioapp.statics.Patterns.DATE_TIME_FORMATTER_TASK;

@Entity
public class ShopCategory extends BaseEntity {

    private String categoryName;

    public ShopCategory() {
    }

    public static ShopCategory apply(ShopCategoryDTO dto) {
        ShopCategory entity = new ShopCategory();
        entity.id = dto.getId();
        entity.categoryName = dto.getCategoryName();
        if (dto.getCreationDateTime() != null)
            entity.creationDate = LocalDateTime.parse(dto.getCreationDateTime(), DATE_TIME_FORMATTER_TASK);
        else entity.creationDate = LocalDateTime.now();
        if (dto.getUpdateDateTime() != null)
            entity.creationDate = LocalDateTime.parse(dto.getUpdateDateTime(), DATE_TIME_FORMATTER_TASK);
        return entity;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "ShopCategory{" +
                "categoryName='" + categoryName + '\'' +
                '}';
    }
}
