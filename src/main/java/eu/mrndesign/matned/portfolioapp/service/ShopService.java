package eu.mrndesign.matned.portfolioapp.service;

import eu.mrndesign.matned.portfolioapp.dto.ShopCategoryDTO;
import eu.mrndesign.matned.portfolioapp.dto.ShopItemDTO;
import eu.mrndesign.matned.portfolioapp.model.ShopCategory;
import eu.mrndesign.matned.portfolioapp.model.ShopItem;
import eu.mrndesign.matned.portfolioapp.repository.ShopCategoryRepository;
import eu.mrndesign.matned.portfolioapp.repository.ShopItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static eu.mrndesign.matned.portfolioapp.statics.Patterns.DATE_TIME_FORMATTER_TASK;

@Service
public class ShopService {

    private final ShopItemRepository shopItemRepository;
    private final ShopCategoryRepository shopCategoryRepository;
    private final FilesService filesService;

    public ShopService(ShopItemRepository shopItemRepository,
                       ShopCategoryRepository shopCategoryRepository,
                       FilesService filesService) {
        this.shopItemRepository = shopItemRepository;
        this.shopCategoryRepository = shopCategoryRepository;
        this.filesService = filesService;
    }


    public List<ShopItemDTO> findAllItems(){
        return convertToShopDTOCollection(shopItemRepository.findAll());
    }

    public ShopItemDTO findItemById(Long id){
        return ShopItemDTO.apply(shopItemRepository.findById(id).orElseThrow(()->new RuntimeException("Entity not found")));
    }

    public List<ShopCategoryDTO> findAllCategories(){
        return convertToCategoryDTOCollection(shopCategoryRepository.findAll());
    }

    public ShopCategoryDTO findCategoryByName(String name){
        return ShopCategoryDTO.apply(shopCategoryRepository.findByCategoryName(name).orElse(shopCategoryRepository.save(new ShopCategory(name))));
    }

    public void addNewItemToShop(ShopItemDTO shopItemDTO, String[] shopCategoryDTO, MultipartFile[] files){
        shopItemDTO.setCreationDateTime(LocalDateTime.now().format(DATE_TIME_FORMATTER_TASK));
        ShopItem shopItem = ShopItem.apply(shopItemDTO);

        shopItem.getCategory().addAll(convertToEntityCollection(shopCategoryDTOConvertFromNames(shopCategoryDTO)));
    }

    private List<ShopCategoryDTO> shopCategoryDTOConvertFromNames(String[] shopCategoryDTO) {
        return Arrays.stream(shopCategoryDTO)
                .map(this::findCategoryByName)
                .collect(Collectors.toList());
    }

    public void editShopItem(Long id, ShopItemDTO shopItemDTO, String[] categories, MultipartFile[] files){
        ShopItem entityToUpdate = ShopItem.apply(shopItemDTO);
        entityToUpdate.setId(id);
        shopItemRepository.save(entityToUpdate);
    }

    public List<ShopCategoryDTO> getItemCategoriesByItemId(Long id) {
        return convertToCategoryDTOCollection(shopItemRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("No such shopItem found."))
                .getCategory());
    }

    public void deleteShopItem(Long id) {
        shopItemRepository.deleteById(id);
    }

//
//         Private

//

    private List<ShopItemDTO> convertToShopDTOCollection(List<ShopItem> entityCollection) {
        return entityCollection.stream()
                .map(ShopItemDTO::apply)
                .collect(Collectors.toList());
    }

    private List<ShopCategory> convertToEntityCollection(List<ShopCategoryDTO> shopCategoryDTO) {
        return shopCategoryDTO.stream()
                .map(ShopCategory::apply)
                .collect(Collectors.toList());
    }

    private List<ShopCategoryDTO> convertToCategoryDTOCollection(List<ShopCategory> entityCollection) {
        return entityCollection.stream()
                .map(ShopCategoryDTO::apply)
                .collect(Collectors.toList());
    }
}
