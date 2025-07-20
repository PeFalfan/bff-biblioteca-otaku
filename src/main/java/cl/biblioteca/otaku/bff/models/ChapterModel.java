package cl.biblioteca.otaku.bff.models;

import io.minio.Result;
import io.minio.messages.Item;

public class ChapterModel {
    private String title;
    private double chapterNumber;
    private String chapterThumbnailUrl;
    private Result<Item> chapter;

    public ChapterModel() { }

    public ChapterModel(String title, double chapterNumber, String chapterThumbnailUrl) {
        this.title = title;
        this.chapterNumber = chapterNumber;
        this.chapterThumbnailUrl = chapterThumbnailUrl;
        this.chapter = null;
    }

    public ChapterModel(String title, double chapterNumber, String chapterThumbnailUrl, Result<Item> chapter) {
        this.title = title;
        this.chapterNumber = chapterNumber;
        this.chapterThumbnailUrl = chapterThumbnailUrl;
        this.chapter = chapter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(double chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getChapterThumbnailUrl() {
        return chapterThumbnailUrl;
    }

    public void setChapterThumbnailUrl(String chapterThumbnailUrl) {
        this.chapterThumbnailUrl = chapterThumbnailUrl;
    }

    public Result<Item> getChapter() {
        return chapter;
    }

    public void setChapter(Result<Item> chapter) {
        this.chapter = chapter;
    }
}
