package eu.mrndesign.matned.portfolioapp.controller;

import eu.mrndesign.matned.portfolioapp.captcha_google.ReCaptchaService;
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
import java.util.List;

@Controller
public class GraphicsController {

    private final GraphicService graphicService;
    private final ReCaptchaService reCaptchaService;

    @Value("spring.servlet.multipart.location")
    private String filePath;

    public GraphicsController(GraphicService graphicService,
                              ReCaptchaService reCaptchaService) {
        this.graphicService = graphicService;
        this.reCaptchaService = reCaptchaService;
    }

    @GetMapping("/graphics")
    public String getGraphics(Model model) {
        model.addAttribute("str", new SearchDTO());
        model.addAttribute("graphics", graphicService.findAll());
        return "graphics-list";
    }

    @PostMapping("/graphics")
    public String searchGraphics(SearchDTO str, Model model) {
        List<GraphicDTO> foundItems = graphicService.findAll(str.getSearched());
        if (foundItems.size() == 0) model.addAttribute("noItemsFound", 1);
        model.addAttribute("str", str);
        model.addAttribute("graphics", foundItems);
        return "graphics-list";
    }

    @GetMapping("/graphics/{id}")
    public String getSingleGraphic(@PathVariable Long id, Model model) {
        model.addAttribute("graphic", graphicService.findById(id));
        return "graphic";
    }

    @GetMapping("/graphics/picture/{id}")
    public String getSinglePicture(@PathVariable Long id, Model model) {
        model.addAttribute("graphic", graphicService.findById(id));
        return "picture";
    }

    @GetMapping("/add-graphic")
    public String getAddNewGraphicForm(Model model,
                                       HttpServletRequest request) {
        model.addAttribute("newGraphic", new GraphicDTO());
        model.addAttribute("chosenSeries", new GraphicSetDTO());
        model.addAttribute("series", graphicService.getAllSeriesMade());
        return "new-picture-add";
    }

    @PostMapping("/add-graphic")
    public String postNewGraphic(@RequestParam("serie") String chosenSeries,
                                 @Validated GraphicDTO graphicDTO,
                                 BindingResult bindingResult,
                                 @RequestParam("file") MultipartFile file,
                                 Model model,
                                 HttpServletRequest request,
                                 @RequestParam(name = "g-recaptcha-response") String captchaResponse) {

        boolean wasFileUploaded = false;
        if (request.isUserInRole(UserRole.Role.ADMIN.roleName())) {
            boolean validatedNotRobot = reCaptchaService.isResponseValid(captchaResponse);
            if (!validatedNotRobot) model.addAttribute("wrong_recaptcha", 1);
            if (!file.isEmpty()) {
                if (validatedNotRobot) {
                    String fileName = System.currentTimeMillis() + file.getOriginalFilename();
                    wasFileUploaded = graphicService.fileUpload(file, fileName);
                    if (wasFileUploaded)
                        graphicService.setGraphicUrl(graphicDTO, fileName);
                    else
                        model.addAttribute("fileUploadError", 1);
                }
            } else {
                model.addAttribute("noFile", 1);
            }

            if (bindingResult.hasErrors() || file.isEmpty() || !wasFileUploaded || !validatedNotRobot) {
                model.addAttribute("error", 1);
                model.addAttribute("binding", bindingResult);
                model.addAttribute("newGraphic", graphicDTO);
                model.addAttribute("series", graphicService.getAllSeriesMade());
                return "new-picture-add";
            }
            graphicService.add(graphicDTO, new GraphicSetDTO(chosenSeries));
            return "redirect:/graphics";
        } else {
            return "accessDenied";
        }

    }


    @GetMapping("/graphics/delete/{id}")
    public String deletePicture(@PathVariable Long id,
                                HttpServletRequest request) {
        if (request.isUserInRole(UserRole.Role.ADMIN.roleName())) {
            graphicService.delete(id);
            return "redirect:/graphics";
        } else {
            return "accessDenied";
        }
    }
}
