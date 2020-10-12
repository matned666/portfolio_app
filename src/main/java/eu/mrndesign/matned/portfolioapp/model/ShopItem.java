package eu.mrndesign.matned.portfolioapp.model;

import eu.mrndesign.matned.portfolioapp.dto.ShopItemDTO;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static eu.mrndesign.matned.portfolioapp.statics.Patterns.DATE_TIME_FORMATTER_TASK;

@Entity
public class ShopItem extends BaseEntity {

    private String title;
    private String description;
    private Integer availableAmount;
    private BigDecimal price;

    @ManyToMany
    private List<ShopCategory> category;

//    @Embedded
//    private ShopItemDetails details;

    public ShopItem() {
    }

    public static ShopItem apply(ShopItemDTO dto){
        ShopItem entity = new ShopItem();
        entity.id = dto.getId();
        entity.title = dto.getTitle();
        entity.description = dto.getDescription();
        entity.availableAmount = dto.getAvailableAmount();
        entity.price = dto.getPrice();
        if (dto.getCreationDateTime() != null) entity.creationDate = LocalDateTime.parse(dto.getCreationDateTime(), DATE_TIME_FORMATTER_TASK);
        else entity.creationDate = LocalDateTime.now();
        if (dto.getUpdateDateTime() != null) entity.updateDate = LocalDateTime.parse(dto.getUpdateDateTime(),DATE_TIME_FORMATTER_TASK);
        ShopItemDetails details = new ShopItemDetails();
//        when more details added - pu'em here
//        entity.details = details;
        return entity;
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

    public Integer getAvailableAmount() {
        return availableAmount;
    }
    public void setAvailableAmount(Integer availableAmount) {
        this.availableAmount = availableAmount;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<ShopCategory> getCategory() {
        return category;
    }
    public void setCategory(List<ShopCategory> category) {
        this.category = category;
    }

//    public ShopItemDetails getDetails() {
//        return details;
//    }
//    public void setDetails(ShopItemDetails details) {
//        this.details = details;
//    }

    @Override
    public String toString() {
        return "ShopItem{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", availableAmount=" + availableAmount +
                '}';
    }
}
