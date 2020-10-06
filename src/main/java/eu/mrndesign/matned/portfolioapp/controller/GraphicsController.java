package eu.mrndesign.matned.portfolioapp.controller;

import eu.mrndesign.matned.portfolioapp.dto.GraphicDTO;
import eu.mrndesign.matned.portfolioapp.dto.GraphicSetDTO;
import eu.mrndesign.matned.portfolioapp.dto.SearchDTO;
import eu.mrndesign.matned.portfolioapp.model.UserRole;
import eu.mrndesign.matned.portfolioapp.service.GraphicService;
import org.springframework.beans.factory.annotation.Value;
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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

@Controller
public class GraphicsController {

    private final GraphicService graphicService;

    @Value("spring.servlet.multipart.location")
    private String filePath;

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

    @GetMapping("/add-graphic")
    public String getAddNewGraphicForm(Model model,
                                       HttpServletRequest request){
            model.addAttribute("newGraphic", new GraphicDTO());
            model.addAttribute("newSeries", new GraphicSetDTO());
            model.addAttribute("chosenSeries", new GraphicSetDTO());
            model.addAttribute("series", graphicService.getAllSeriesMade());
            return "new-picture-add";
    }

    @PostMapping("/add-graphic")
    public String postNewGraphic(
//            @Validated GraphicDTO graphicDTO,
                                 @RequestParam("file") MultipartFile file
//                                 BindingResult bindingResult,
//                                 Model model
//                                 HttpServletRequest request
    )
    {
//        if (!file.isEmpty())
            graphicService.fileUpload(file);
//        if (!request.isUserInRole(UserRole.Role.ADMIN.roleName())) {
//            if(file != null){
//                Path fileNameWithPath = Paths.get(filePath, file.getOriginalFilename());
//                try {
//                    Files.write(fileNameWithPath, file.getBytes());
////                    graphicDTO.setImageUrl("/img/content/"+file.getOriginalFilename());
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                model.addAttribute("noFile", 1);
//            }

//            if (bindingResult.hasErrors() || file == null) {
//                model.addAttribute("error", 1);
//                model.addAttribute("binding", bindingResult);
//                model.addAttribute("newGraphic", graphicDTO);
//                return "new-picture-add";
//            }
//            graphicService.add(graphicDTO);
            return "redirect:/graphics";
//        }else {
//            return "accessDenied";
//        }

    }
}
