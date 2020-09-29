package eu.mrndesign.matned.portfolioapp.model;

import javax.persistence.Entity;

@Entity
public class GraphicSet extends BaseEntity{

    private String setName;

    public GraphicSet() {
    }

    public GraphicSet(String setName) {
        this.setName = setName;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }
}
