package eu.mrndesign.matned.portfolioapp.controller;

import eu.mrndesign.matned.portfolioapp.service.GraphicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GraphicsController {

    private final GraphicService graphicService;

    public GraphicsController(GraphicService graphicService) {
        this.graphicService = graphicService;
    }

    @GetMapping("/graphics")
    public String getGraphics(Model model){
        model.addAttribute("graphics", graphicService.findAll());
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
