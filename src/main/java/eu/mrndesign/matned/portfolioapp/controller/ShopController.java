package eu.mrndesign.matned.portfolioapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShopController {

    @GetMapping("/shop")
    public String getShop(){
        return "shop";
    }

    @GetMapping("/basket")
    public String getBasket(){
        return "basket";
    }

}
