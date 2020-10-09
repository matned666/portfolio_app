package eu.mrndesign.matned.portfolioapp.controller;

import eu.mrndesign.matned.portfolioapp.captcha_google.ReCaptchaService;
import eu.mrndesign.matned.portfolioapp.dto.GraphicDTO;
import eu.mrndesign.matned.portfolioapp.dto.GraphicSetDTO;
import eu.mrndesign.matned.portfolioapp.dto.ProjectDTO;
import eu.mrndesign.matned.portfolioapp.dto.SearchDTO;
import eu.mrndesign.matned.portfolioapp.model.UserRole;
import eu.mrndesign.matned.portfolioapp.service.ProjectService;
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
public class ProjectsController {

    private final ProjectService projectService;
    private final ReCaptchaService reCaptchaService;

    public ProjectsController(ProjectService projectService,
                              ReCaptchaService reCaptchaService) {
        this.projectService = projectService;
        this.reCaptchaService = reCaptchaService;
    }

    @GetMapping("/projects")
    public String getProjects(Model model) {
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("str", new SearchDTO());
        return "projects-list";
    }

    @PostMapping("/projects")
    public String searchProjects(SearchDTO str, Model model) {
        List<ProjectDTO> foundItems = projectService.findAll("%" + str.getSearched() + "%");
        if (foundItems.size() == 0) model.addAttribute("noItemsFound", 1);
        model.addAttribute("projects", foundItems);
        model.addAttribute("str", str);
        return "projects-list";
    }

    @GetMapping("/projects/{id}")
    public String getSingleGraphic(@PathVariable Long id, Model model) {
        model.addAttribute("project", projectService.findById(id));
        model.addAttribute("images", projectService.findAllImagesByProject(id));
        return "project";

    }

    @GetMapping("/projects-admin/add")
    public String getAddNewProjectForm(Model model,
                                       HttpServletRequest request) {
        if (request.isUserInRole(UserRole.Role.ADMIN.roleName())) {
            model.addAttribute("newProject", new ProjectDTO());
            return "project-add";
        } else return "accessDenied";
    }

    @PostMapping("/projects-admin/add")
    public String postNewProject(@Validated ProjectDTO projectDTO,
                                 BindingResult bindingResult,
                                 @RequestParam("files") MultipartFile[] files,
                                 Model model,
                                 HttpServletRequest request,
                                 @RequestParam(name = "g-recaptcha-response")
                                         String captchaResponse) {
        boolean wereFilesUploaded = files.length > 0;
        if (request.isUserInRole(UserRole.Role.ADMIN.roleName())) {
            boolean validatedNotRobot = reCaptchaService.isResponseValid(captchaResponse);
            boolean loadedFilesWithNoProblem = true;
            if (!validatedNotRobot) model.addAttribute("wrong_recaptcha", 1);

            if (bindingResult.hasErrors() || !validatedNotRobot) {
                model.addAttribute("error", 1);
                model.addAttribute("binding", bindingResult);
                model.addAttribute("newProject", projectDTO);
                return "project-add";
            }
            ProjectDTO filledDTO = projectService.add(projectDTO);
            if (wereFilesUploaded) {
                String[] filesNames = projectService.uploadProjectFiles(files, filledDTO.getId());
                if (filesNames.length != files.length) {
                    loadedFilesWithNoProblem = false;
                    return "redirect:/projects?loadingFiles=1";
                }
            }
            return "redirect:/projects";
        } else {
            return "accessDenied";
        }

    }

}
