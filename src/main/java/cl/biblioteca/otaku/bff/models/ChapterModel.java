package cl.biblioteca.otaku.bff.models;

public class ChapterModel {
    private Long id;
    private String title;
    private int chapterNumber;
    private String chapterDescription;
    private String chapterThumbnailUrl;

    public ChapterModel() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getChapterDescription() {
        return chapterDescription;
    }

    public void setChapterDescription(String chapterDescription) {
        this.chapterDescription = chapterDescription;
    }

    public String getChapterThumbnailUrl() {
        return chapterThumbnailUrl;
    }

    public void setChapterThumbnailUrl(String chapterThumbnailUrl) {
        this.chapterThumbnailUrl = chapterThumbnailUrl;
    }
}
