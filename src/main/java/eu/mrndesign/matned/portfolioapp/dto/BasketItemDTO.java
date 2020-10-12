package eu.mrndesign.matned.portfolioapp.dto;

import eu.mrndesign.matned.portfolioapp.model.ShopItem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class BasketItemDTO implements Serializable {

    private ShopItem shopItem;
    private Integer amount;
    private BigDecimal totalPrice;

    public BasketItemDTO(ShopItem shopItem, Integer amount) {
        this.shopItem = shopItem;
        this.amount = amount;
        totalPrice = shopItem.getPrice().multiply(BigDecimal.valueOf(amount));
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ShopItem getShopItem() {
        return shopItem;
    }
    public void setShopItem(ShopItem shopItem) {
        this.shopItem = shopItem;
    }

    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasketItemDTO that = (BasketItemDTO) o;
        return shopItem.equals(that.shopItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shopItem);
    }
}
