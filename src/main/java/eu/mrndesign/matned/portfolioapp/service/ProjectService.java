package eu.mrndesign.matned.portfolioapp.service;

import eu.mrndesign.matned.portfolioapp.dto.ProjectDTO;
import eu.mrndesign.matned.portfolioapp.dto.ProjectImageDTO;
import eu.mrndesign.matned.portfolioapp.model.Project;
import eu.mrndesign.matned.portfolioapp.model.ProjectImage;
import eu.mrndesign.matned.portfolioapp.repository.ProjectImageRepository;
import eu.mrndesign.matned.portfolioapp.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    public static final String PROJECT_ERROR = "Error finding project - temp database problem";
    @Value("${ftp.server.host}")
    private String ftpHost;

    @Value("${ftp.server.path}")
    private String ftpPath;

    private final FilesService filesService;
    private final ProjectRepository projectRepository;
    private final ProjectImageRepository projectImageRepository;

    public ProjectService(FilesService filesService,
                          ProjectRepository projectRepository,
                          ProjectImageRepository projectImageRepository) {
        this.filesService = filesService;
        this.projectRepository = projectRepository;
        this.projectImageRepository = projectImageRepository;
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

    public List<ProjectDTO> findAll(String searched) {
        return projectRepository.findAll(searched).stream()
                .map(ProjectDTO::apply)
                .collect(Collectors.toList());
    }

    public List<ProjectImageDTO> findAllImagesByProject(Long id){
        return applyProjectGraphicListToDTO(projectImageRepository.findByProjectId(id));
    }

    public ProjectImageDTO getFirstProjectImage(Long projectId){
        return ProjectImageDTO.apply(projectImageRepository.findByProjectId(projectId).stream()
                .findFirst()
                .orElse(null));
    }





    private List<ProjectImageDTO> applyProjectGraphicListToDTO(List<ProjectImage> projectEntities) {
        return projectEntities.stream()
                .map(ProjectImageDTO::apply)
                .collect(Collectors.toList());
    }


    public String[] uploadProjectFiles(MultipartFile[] files, Long id) {
        String[] fileNames = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            String originalFilename = files[i].getOriginalFilename();
            fileNames[i] = getFileName(originalFilename);
            String title = "Project image " + id;
            String imageUrl = "http://" + ftpHost + ftpPath + "/" + originalFilename;
            projectImageRepository.save(
                    new ProjectImage(
                            title,
                            imageUrl,
                            projectRepository.findById(id).orElseThrow(()-> new RuntimeException(PROJECT_ERROR))));
        }
        filesService.multipleFilesUpload(files, fileNames);
        return fileNames;
    }

    private String getFileName(String originalName){
        return System.currentTimeMillis()+originalName;
    }

    public ProjectDTO add(ProjectDTO projectDTO) {
        return ProjectDTO.apply(projectRepository.save(Project.apply(projectDTO)));
    }
}
