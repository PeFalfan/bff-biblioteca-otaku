package cl.biblioteca.otaku.bff.models;

public class FavoritePostRequest {
    private String email;
    private String title;

    public FavoritePostRequest() {
    }

    public FavoritePostRequest(String email, String title) {
        this.email = email;
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
