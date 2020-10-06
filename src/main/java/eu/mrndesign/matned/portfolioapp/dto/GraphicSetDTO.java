package eu.mrndesign.matned.portfolioapp.dto;

import eu.mrndesign.matned.portfolioapp.model.GraphicSet;

public class GraphicSetDTO {

    private String setName;

    public GraphicSetDTO() {
    }

    public GraphicSetDTO(String setName) {
        this.setName = setName;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public static GraphicSetDTO apply(GraphicSet graphicSet) {
        return new GraphicSetDTO(graphicSet.getSetName());
    }
}
