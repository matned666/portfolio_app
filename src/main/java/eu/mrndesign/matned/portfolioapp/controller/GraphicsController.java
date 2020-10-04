package eu.mrndesign.matned.portfolioapp.controller;

import eu.mrndesign.matned.portfolioapp.dto.GraphicDTO;
import eu.mrndesign.matned.portfolioapp.dto.SearchDTO;
import eu.mrndesign.matned.portfolioapp.service.GraphicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class GraphicsController {

    private final GraphicService graphicService;

    public GraphicsController(GraphicService graphicService) {
        this.graphicService = graphicService;
    }

    @GetMapping("/graphics")
    public String getGraphics(Model model){
        model.addAttribute("str", new SearchDTO());
        model.addAttribute("graphics", graphicService.findAll());
        return "graphics-list";
    }

    @PostMapping("/graphics")
    public String searchGraphics(SearchDTO str, Model model){
        List<GraphicDTO> foundItems = graphicService.findAll(str.getSearched());
        if (foundItems.size() == 0) model.addAttribute("noItemsFound", 1);
        model.addAttribute("str", str);
        model.addAttribute("graphics", foundItems);
        return "graphics-list";
    }

    @GetMapping("/graphics/{id}")
    public String getSingleGraphic(@PathVariable Long id, Model model){
        model.addAttribute("graphic", graphicService.findById(id));
        return "graphic";
    }

    @GetMapping("/graphics/picture/{id}")
    public String getSinglePicture(@PathVariable Long id, Model model){
        model.addAttribute("graphic", graphicService.findById(id));
        return "picture";
    }


}
