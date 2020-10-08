package eu.mrndesign.matned.portfolioapp.service;

import eu.mrndesign.matned.portfolioapp.dto.GraphicDTO;
import eu.mrndesign.matned.portfolioapp.dto.GraphicSetDTO;
import eu.mrndesign.matned.portfolioapp.ftp.FtpClient;
import eu.mrndesign.matned.portfolioapp.model.Graphic;
import eu.mrndesign.matned.portfolioapp.model.GraphicSet;
import eu.mrndesign.matned.portfolioapp.repository.GraphicRepository;
import eu.mrndesign.matned.portfolioapp.repository.GraphicSetRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static eu.mrndesign.matned.portfolioapp.statics.Patterns.DATE_TIME_FORMATTER_ONLY_DATE;

@Service

public class GraphicService {


    @Value("${ftp.server.host}")
    private String ftpHost;

    @Value("${ftp.server.port}")
    private Integer ftpPort;

    @Value("${ftp.server.user}")
    private String ftpUser;

    @Value("${ftp.server.password}")
    private String ftpPassword;

    @Value("${ftp.server.path}")
    private String ftpPath;

    private final GraphicRepository graphicRepository;
    private final GraphicSetRepository graphicSetRepository;

    public GraphicService(GraphicRepository graphicRepository,
                          GraphicSetRepository graphicSetRepository) {
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

    public boolean fileUpload(MultipartFile file, String fileName) {
        try {
            FtpClient ftp = new FtpClient(ftpHost, ftpPort, ftpUser, ftpPassword);
            ftp.open();

            File physicalFile = File.createTempFile(System.currentTimeMillis() + "tmp", "jpg");
            file.transferTo(physicalFile);
            ftp.putFileToPath(physicalFile, fileName);
            ftp.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
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
        FtpClient ftp = new FtpClient(ftpHost, ftpPort, ftpUser, ftpPassword);
        try {
            ftp.open();
            ftp.deleteFile(path);
            ftp.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP exception found");
        }
    }

    private String getFileNameFromPath(String path) {
        return path.split("/")[path.split("/").length - 1];
    }


}
