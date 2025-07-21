package cl.biblioteca.otaku.bff.models;

public class FavoriteModel {

    private String title;
    private String mainTag;

    public FavoriteModel() {
    }

    public FavoriteModel(String title, String mainTag) {
        this.title = title;
        this.mainTag = mainTag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainTag() {
        return mainTag;
    }

    public void setMainTag(String mainTag) {
        this.mainTag = mainTag;
    }
}
