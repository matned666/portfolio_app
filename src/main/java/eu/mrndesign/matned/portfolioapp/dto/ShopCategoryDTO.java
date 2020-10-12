package eu.mrndesign.matned.portfolioapp.dto;

import eu.mrndesign.matned.portfolioapp.model.ShopCategory;

import java.time.LocalDateTime;

import static eu.mrndesign.matned.portfolioapp.statics.Patterns.DATE_TIME_FORMATTER_TASK;

public class ShopCategoryDTO {

    private Long id;
    private String categoryName;
    private String creationDateTime;
    private String updateDateTime;

    public ShopCategoryDTO() {
    }

    public ShopCategoryDTO(String categoryName) {
        this.categoryName = categoryName;
    }

    public static ShopCategoryDTO apply(ShopCategory entity){
        ShopCategoryDTO dto = new ShopCategoryDTO(entity.getCategoryName());
        dto.creationDateTime = entity.getCreationDate().format(DATE_TIME_FORMATTER_TASK);
        if (entity.getUpdateDate() != null) dto.updateDateTime = entity.getUpdateDate().format(DATE_TIME_FORMATTER_TASK);
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public String getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(String updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    @Override
    public String toString() {
        return "ShopCategoryDTO{" +
                "categoryName='" + categoryName + '\'' +
                ", creationDateTime='" + creationDateTime + '\'' +
                ", updateDateTime='" + updateDateTime + '\'' +
                '}';
    }
}
