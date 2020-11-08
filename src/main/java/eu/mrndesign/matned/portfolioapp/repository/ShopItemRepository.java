package eu.mrndesign.matned.portfolioapp.repository;

import eu.mrndesign.matned.portfolioapp.model.ProjectImage;
import eu.mrndesign.matned.portfolioapp.model.ShopItem;
import eu.mrndesign.matned.portfolioapp.model.ShopItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopItemRepository extends JpaRepository<ShopItem, Long> {

    @Query("select sii from ShopItemImage sii where sii.shopItem.id = ?1")
    List<ShopItem> findByProjectId(Long projectId);

    @Query("select case when count(sii) > 0 then true else false end from ShopItemImage sii where lower(sii.imageUrl) like lower(?1)")
    boolean existsByImageUrl(String imageUrl);

}
