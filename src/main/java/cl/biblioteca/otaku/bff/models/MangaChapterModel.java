package cl.biblioteca.otaku.bff.models;

import io.minio.Result;
import io.minio.messages.Item;

public class MangaChapterModel {
    private double chapterNumber;
    private String chapterName;
    private Result<Item> chapter;

    public MangaChapterModel() {
    }

    public MangaChapterModel(double chapterNumber, String chapterName, Result<Item> chapter) {
        this.chapterNumber = chapterNumber;
        this.chapterName = chapterName;
        this.chapter = chapter;
    }

    public double getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(double chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public Result<Item> getChapter() {
        return chapter;
    }

    public void setChapter(Result<Item> chapter) {
        this.chapter = chapter;
    }
}
