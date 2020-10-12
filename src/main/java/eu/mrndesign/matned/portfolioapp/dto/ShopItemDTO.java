package eu.mrndesign.matned.portfolioapp.dto;

import eu.mrndesign.matned.portfolioapp.model.ShopItem;
import eu.mrndesign.matned.portfolioapp.validation.IsNumericBigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

import static eu.mrndesign.matned.portfolioapp.statics.Patterns.DATE_TIME_FORMATTER_TASK;

public class ShopItemDTO {

    private Long id;

    @NotEmpty(message = "The title cannot be empty")
    @Size(min = 10, max = 100, message = "Title should be between {min} and {max} signs")
    private String title;

    @NotEmpty(message = "The title cannot be empty")
    @Size(min = 20, message = "Minimal {min} signs")
    private String description;

    @NotEmpty(message = "You should give an amount")
    @Positive(message = "The value should be positive")
    private Integer availableAmount;

    @IsNumericBigDecimal(message = "The value should be a proper number")
    @Positive(message = "The value should be positive")
    private BigDecimal price;

//    AUDIT
    private String creationDateTime;
    private String updateDateTime;

    public ShopItemDTO() {
    }

    public ShopItemDTO(String title,
                       String description,
                       Integer availableAmount,
                       BigDecimal price) {
        this.title = title;
        this.description = description;
        this.availableAmount = availableAmount;
        this.price = price;
    }

    public static ShopItemDTO apply(ShopItem entity) {
        ShopItemDTO dto = new ShopItemDTO(
                entity.getTitle(),
                entity.getDescription(),
                entity.getAvailableAmount(),
                entity.getPrice()
        );
//        When shop items details are expanded , add all fields to convert - here
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
        return "ShopItemDTO{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", availableAmount=" + availableAmount +
                ", price=" + price +
                ", creationDateTime='" + creationDateTime + '\'' +
                ", updateDateTime='" + updateDateTime + '\'' +
                '}';
    }
}
