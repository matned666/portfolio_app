package eu.mrndesign.matned.portfolioapp.service;

import eu.mrndesign.matned.portfolioapp.dto.ProjectDTO;
import eu.mrndesign.matned.portfolioapp.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<ProjectDTO> findAll(){
        return projectRepository.findAll().stream()
                .map(ProjectDTO::apply)
                .collect(Collectors.toList());
    }

    public ProjectDTO findById(Long id){
        return ProjectDTO.apply(projectRepository.findById(id)
                .orElseThrow(()->new RuntimeException("No project found")));
    }
}
