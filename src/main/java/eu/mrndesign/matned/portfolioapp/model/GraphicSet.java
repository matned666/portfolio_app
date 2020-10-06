package eu.mrndesign.matned.portfolioapp.model;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class GraphicSet extends BaseEntity{

    private String setName;

    public GraphicSet() {
        creationDate = LocalDateTime.now();
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
