package eu.mrndesign.matned.portfolioapp.controller;

import eu.mrndesign.matned.portfolioapp.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProjectsController {

    private final ProjectService projectService;

    public ProjectsController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping("/projects")
    public String getProjects(Model model){
        model.addAttribute("projects", projectService.findAll());
        return "projects-list";
    }

    @GetMapping("/projects/{id}")
    public String getSingleGraphic(@PathVariable Long id, Model model){
        model.addAttribute("project", projectService.findById(id));
        return "project";

    }
}
