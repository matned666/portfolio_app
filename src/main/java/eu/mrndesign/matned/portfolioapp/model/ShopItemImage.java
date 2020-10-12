package eu.mrndesign.matned.portfolioapp.model;

import eu.mrndesign.matned.portfolioapp.dto.ProjectImageDTO;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

import static eu.mrndesign.matned.portfolioapp.statics.Patterns.DATE_TIME_FORMATTER_TASK;

@Entity
public class ShopItemImage extends BaseImageEntity{

    @ManyToOne
    private ShopItem shopItem;

    public ShopItemImage() {
    }

    public ShopItemImage(String title, String imageUrl, ShopItem shopItem) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.shopItem = shopItem;
        this.creationDate = LocalDateTime.now();
    }

    public static ShopItemImage apply(ProjectImageDTO dto, ShopItem shopItem){
        ShopItemImage entity = new ShopItemImage(dto.getTitle(), dto.getImageUrl(), shopItem);
        return (ShopItemImage) getImageBaseData(dto, entity);
    }

    public ShopItem getProject() {
        return shopItem;
    }

    public void setProject(ShopItem shopItem) {
        this.shopItem = shopItem;
    }

    @Override
    public String toString() {
        return "ShopItemImage{" +
                "shopItem=" + shopItem +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
