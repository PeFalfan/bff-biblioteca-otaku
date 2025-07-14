package cl.biblioteca.otaku.bff.models;

import java.util.ArrayList;

public class MangaDataModel {
    private Long id;
    private String title;
    private int currentChapters;
    private String mainTag;
    private String description;
    private ArrayList<MangaChapterModel> chapters;
    private String coverUrl;
    private int yearOfRelease;

    public MangaDataModel() {
    }

    public MangaDataModel(
            Long id,
            String title,
            int currentChapters,
            String mainTag,
            String description,
            ArrayList<MangaChapterModel> chapters,
            String coverUrl,
            int yearOfRelease) {
        this.id = id;
        this.title = title;
        this.currentChapters = currentChapters;
        this.mainTag = mainTag;
        this.description = description;
        this.chapters = chapters;
        this.coverUrl = coverUrl;
        this.yearOfRelease = yearOfRelease;
    }

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

    public int getCurrentChapters() {
        return currentChapters;
    }

    public void setCurrentChapters(int currentChapters) {
        this.currentChapters = currentChapters;
    }

    public String getMainTag() {
        return mainTag;
    }

    public void setMainTag(String mainTag) {
        this.mainTag = mainTag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<MangaChapterModel> getChapters() {
        return chapters;
    }

    public void setChapters(ArrayList<MangaChapterModel> chapters) {
        this.chapters = chapters;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }
}
