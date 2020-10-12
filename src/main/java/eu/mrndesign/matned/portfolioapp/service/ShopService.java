package eu.mrndesign.matned.portfolioapp.service;

import eu.mrndesign.matned.portfolioapp.dto.ShopCategoryDTO;
import eu.mrndesign.matned.portfolioapp.dto.ShopItemDTO;
import eu.mrndesign.matned.portfolioapp.model.ShopCategory;
import eu.mrndesign.matned.portfolioapp.model.ShopItem;
import eu.mrndesign.matned.portfolioapp.repository.ShopCategoryRepository;
import eu.mrndesign.matned.portfolioapp.repository.ShopItemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static eu.mrndesign.matned.portfolioapp.statics.Patterns.DATE_TIME_FORMATTER_TASK;

@Service
public class ShopService {

    private final ShopItemRepository shopItemRepository;
    private final ShopCategoryRepository shopCategoryRepository;

    public ShopService(ShopItemRepository shopItemRepository,
                       ShopCategoryRepository shopCategoryRepository) {
        this.shopItemRepository = shopItemRepository;
        this.shopCategoryRepository = shopCategoryRepository;
    }

    public void addNewItemToShop(ShopItemDTO shopItemDTO, List<ShopCategoryDTO> shopCategoryDTO){
        shopItemDTO.setCreationDateTime(LocalDateTime.now().format(DATE_TIME_FORMATTER_TASK));
        ShopItem shopItem = ShopItem.apply(shopItemDTO);

        shopItem.getCategory().addAll(convertCollection(shopCategoryDTO));
    }

    private List<ShopCategory> convertCollection(List<ShopCategoryDTO> shopCategoryDTO) {
        List<ShopCategory> result = new LinkedList();
        for (ShopCategoryDTO dto : shopCategoryDTO) {
            result.add(ShopCategory.apply(dto));
        }
        return result;
    }
}
