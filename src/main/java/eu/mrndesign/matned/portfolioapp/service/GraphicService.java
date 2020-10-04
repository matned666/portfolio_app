package eu.mrndesign.matned.portfolioapp.service;

import eu.mrndesign.matned.portfolioapp.dto.GraphicDTO;
import eu.mrndesign.matned.portfolioapp.repository.GraphicRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GraphicService {

    private final GraphicRepository graphicRepository;

    public GraphicService(GraphicRepository graphicRepository) {
        this.graphicRepository = graphicRepository;
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
}
