package eu.mrndesign.matned.portfolioapp.controller;

import eu.mrndesign.matned.portfolioapp.dto.GraphicDTO;
import eu.mrndesign.matned.portfolioapp.dto.ProjectDTO;
import eu.mrndesign.matned.portfolioapp.dto.SearchDTO;
import eu.mrndesign.matned.portfolioapp.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProjectsController {

    private final ProjectService projectService;

    public ProjectsController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping("/projects")
    public String getProjects(Model model){
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("str", new SearchDTO());
        return "projects-list";
    }

    @PostMapping("/projects")
    public String searchProjects(SearchDTO str, Model model){
        List<ProjectDTO> foundItems = projectService.findAll("%"+str.getSearched()+"%");
        if (foundItems.size() == 0) model.addAttribute("noItemsFound", 1);
        model.addAttribute("projects", foundItems);
        model.addAttribute("str", str);
        return "projects-list";
    }

    @GetMapping("/projects/{id}")
    public String getSingleGraphic(@PathVariable Long id, Model model){
        model.addAttribute("project", projectService.findById(id));
        return "project";

    }
}
