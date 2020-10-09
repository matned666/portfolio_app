package eu.mrndesign.matned.portfolioapp.service;

import eu.mrndesign.matned.portfolioapp.dto.GraphicDTO;
import eu.mrndesign.matned.portfolioapp.dto.GraphicSetDTO;
import eu.mrndesign.matned.portfolioapp.model.Graphic;
import eu.mrndesign.matned.portfolioapp.model.GraphicSet;
import eu.mrndesign.matned.portfolioapp.repository.GraphicRepository;
import eu.mrndesign.matned.portfolioapp.repository.GraphicSetRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static eu.mrndesign.matned.portfolioapp.statics.Patterns.DATE_TIME_FORMATTER_ONLY_DATE;

@Service

public class GraphicService {

    @Value("${ftp.server.host}")
    private String ftpHost;

    @Value("${ftp.server.path}")
    private String ftpPath;

    private final FilesService filesService;
    private final GraphicRepository graphicRepository;
    private final GraphicSetRepository graphicSetRepository;

    public GraphicService(FilesService filesService,
                          GraphicRepository graphicRepository,
                          GraphicSetRepository graphicSetRepository) {
        this.filesService = filesService;
        this.graphicRepository = graphicRepository;
        this.graphicSetRepository = graphicSetRepository;
    }

    public List<GraphicDTO> findAll() {
        return graphicRepository.findAll().stream()
                .map(GraphicDTO::apply)
                .collect(Collectors.toList());

    }

    public GraphicDTO findById(Long id) {
        return GraphicDTO.apply(graphicRepository.findById(id).orElseThrow(() -> new RuntimeException("No graphic found")));
    }

    public List<GraphicDTO> findAll(String str) {
        return graphicRepository.findAll("%" + str + "%").stream()
                .map(GraphicDTO::apply)
                .collect(Collectors.toList());
    }

    public void add(GraphicDTO graphicDTO, GraphicSetDTO chosenSeries) {
        GraphicSet set = addSeries(chosenSeries);
        graphicRepository.save(Graphic.apply(graphicDTO, set, false));
    }

    public List<GraphicSetDTO> getAllSeriesMade() {
        return graphicSetRepository.findAll().stream()
                .map(GraphicSetDTO::apply)
                .collect(Collectors.toList());
    }

    public void setGraphicUrl(GraphicDTO graphicDTO, String fileName) {
        graphicDTO.setImageUrl("http://" + ftpHost + ftpPath + "/" + fileName);
    }

    public GraphicSet addSeries(GraphicSetDTO chosenSeries) {
        GraphicSet set = new GraphicSet();
        if (!graphicSetRepository.existsBySetName(chosenSeries.getSetName())) {
            set.setSetName(chosenSeries.getSetName());
            graphicSetRepository.save(set);
            return set;
        } else
            return graphicSetRepository.findBySetName(chosenSeries.getSetName())
                    .orElseThrow(() -> new RuntimeException("Series repository problem"));
    }

    public void delete(Long id) {
        deleteFileByPath(id);
        graphicRepository.deleteById(id);
    }

    public void edit(Long id, GraphicDTO fromDTO, String series){
        Graphic to = graphicRepository
                .findById(id)
                .orElseThrow(()->
                        new RuntimeException(
                                "Database problem with finding the graphic which actually exists there."));
        applyEditedData(to, fromDTO, series);
        graphicRepository.save(to);
    }









    private void applyEditedData(Graphic to, GraphicDTO fromGDTO, String series){
        to.setTitle(fromGDTO.getTitle());
        to.setDescription(fromGDTO.getDescription());
        if (fromGDTO.getImageUrl()!=null) to.setImageUrl(fromGDTO.getImageUrl());
        to.setCopiesMade(fromGDTO.getCopiesMade());
        to.setNumberOfCopies(fromGDTO.getNumberOfCopies());
        if (fromGDTO.getDateOfPublication() != null)
            to.setDateOfPublication(LocalDate.parse(fromGDTO.getDateOfPublication(), DATE_TIME_FORMATTER_ONLY_DATE));
        to.setSerie(addSeries(new GraphicSetDTO(series)));
    }

    private void deleteFileByPath(Long id) {
        String path = getFileNameFromPath(
                graphicRepository.findById(id).orElseThrow(() -> new RuntimeException("No file found")).getImageUrl());
        filesService.deleteFile(path);
    }


    private String getFileNameFromPath(String path) {
        return path.split("/")[path.split("/").length - 1];
    }


}
