package eu.mrndesign.matned.portfolioapp.dto;

public class SearchDTO {

    private String searched;

    public SearchDTO() {
    }

    public SearchDTO(String searched) {
        this.searched = searched;
    }

    public String getSearched() {
        return searched;
    }

    public void setSearched(String searched) {
        this.searched = searched;
    }
}
