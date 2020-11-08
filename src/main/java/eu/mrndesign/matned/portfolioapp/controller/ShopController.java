package eu.mrndesign.matned.portfolioapp.controller;

import eu.mrndesign.matned.portfolioapp.dto.ShopItemDTO;
import eu.mrndesign.matned.portfolioapp.model.UserRole;
import eu.mrndesign.matned.portfolioapp.service.ShopService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/shop")
    public String getShop(Model model) {
        model.addAttribute("shopItems", shopService.findAllItems());
        model.addAttribute("categories", shopService.findAllCategories());
        return "shop";
    }

    @GetMapping("/shop/{id}")
    public String getShopItem(@PathVariable Long id, Model model) {
        model.addAttribute("shopItem", shopService.findItemById(id));
        model.addAttribute("itemCategory", shopService.getItemCategoriesByItemId(id));
        return "shopItem";
    }

    @GetMapping("/shop/admin/addItem")
    public String addNewItem(Model model,
                             HttpServletRequest request) {
        if (request.isUserInRole(UserRole.Role.ADMIN.roleName())) {
            model.addAttribute("newItem", new ShopItemDTO());
            model.addAttribute("categories", shopService.findAllCategories());
            return "shopitem-add";
        } else return "accessDenied";
    }

    @GetMapping("/shop/admin/editItem/{id}")
    public String editItem(@PathVariable Long id,
                           Model model,
                           HttpServletRequest request) {
        if (request.isUserInRole(UserRole.Role.ADMIN.roleName())) {
            model.addAttribute("editedItem", shopService.findItemById(id));
            model.addAttribute("categories", shopService.findAllCategories());
            return "shopitem-edit";
        } else return "accessDenied";
    }

    @PostMapping("/shop/admin/add")
    public String postAddedItem(@RequestParam("categories") String[] categories,
                                @Validated ShopItemDTO shopItemDTO,
                                BindingResult bindingResult,
                                @RequestParam("files") MultipartFile[] files,
                                Model model,
                                HttpServletRequest request) {
        if (request.isUserInRole(UserRole.Role.ADMIN.roleName())) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("newItem", shopItemDTO);
                model.addAttribute("categories", shopService.findAllCategories());
                model.addAttribute("binding", bindingResult);
                model.addAttribute("errors", 1);
                return "/shop/admin/add";
            }
            shopService.addNewItemToShop(shopItemDTO, categories, files);
            return "shop";
        } else return "accessDenied";
    }

    @PostMapping("/shop/admin/editItem/{id}")
    public String postEditShopItem(@PathVariable Long id,
                                   @RequestParam("categories") String[] categories,
                                   @Validated ShopItemDTO shopItemDTO,
                                   BindingResult bindingResult,
                                   @RequestParam("files") MultipartFile[] files,
                                   Model model,
                                   HttpServletRequest request) {
        if (request.isUserInRole(UserRole.Role.ADMIN.roleName())) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("newItem", shopItemDTO);
                model.addAttribute("categories", shopService.findAllCategories());
                model.addAttribute("binding", bindingResult);
                model.addAttribute("errors", 1);
                return "/shop/admin/add";
            }
            shopService.editShopItem(id, shopItemDTO, categories, files);
            return "redirect:/shop/" + id;
        } else return "accessDenied";
    }

    @GetMapping("/shop/admin/delete/{id}")
    public String deleteShopItem(@PathVariable Long id,
                                 HttpServletRequest request) {
        if (request.isUserInRole(UserRole.Role.ADMIN.roleName())) {
            shopService.deleteShopItem(id);
            return "redirect:/shop";
        } else return "accessDenied";
    }

    @GetMapping("/basket")
    public String getBasket() {
        return "basket";
    }

}
