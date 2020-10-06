package eu.mrndesign.matned.portfolioapp.service;

import eu.mrndesign.matned.portfolioapp.dto.GraphicDTO;
import eu.mrndesign.matned.portfolioapp.dto.GraphicSetDTO;
import eu.mrndesign.matned.portfolioapp.model.Graphic;
import eu.mrndesign.matned.portfolioapp.model.GraphicSet;
import eu.mrndesign.matned.portfolioapp.repository.GraphicRepository;
import eu.mrndesign.matned.portfolioapp.repository.GraphicSetRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class GraphicService {

    @Value("${files.path}")
    private String filesPath;

    private final GraphicRepository graphicRepository;
    private final GraphicSetRepository graphicSetRepository;

    public GraphicService(GraphicRepository graphicRepository,
                          GraphicSetRepository graphicSetRepository) {
        this.graphicRepository = graphicRepository;
        this.graphicSetRepository = graphicSetRepository;
    }

    public List<GraphicDTO> findAll(){
        return graphicRepository.findAll().stream()
                .map(GraphicDTO::apply)
                .collect(Collectors.toList());

    }

    public GraphicDTO findById(Long id) {
        return GraphicDTO.apply(graphicRepository.findById(id).orElseThrow(()->new RuntimeException("No graphic found")));
    }

    public List<GraphicDTO> findAll(String str) {
        return graphicRepository.findAll("%"+str+"%").stream()
                .map(GraphicDTO::apply)
                .collect(Collectors.toList());
    }

    public void add(GraphicDTO graphicDTO) {
        GraphicSet set = new GraphicSet(graphicDTO.getSeries());
        if (graphicDTO.getSeries() != null) {
            if (!graphicDTO.getSeries().trim().equals("")) {
                graphicSetRepository.save(set);
            }
        }
        graphicRepository.save(Graphic.apply(graphicDTO, set));
    }

    public List<GraphicSetDTO> getAllSeriesMade() {
        return graphicSetRepository.findAll().stream()
                .map(GraphicSetDTO::apply)
                .collect(Collectors.toList());
    }

    public void fileUpload(MultipartFile file) {
            try {
                file.transferTo(new File("resources/img/content/upload/"+file.getOriginalFilename()));

            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
