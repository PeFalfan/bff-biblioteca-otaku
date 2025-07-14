package cl.biblioteca.otaku.bff.service;

import cl.biblioteca.otaku.bff.models.MangaDataModel;
import reactor.core.publisher.Mono;

import java.util.List;

public interface MangaService {

    Mono<List<MangaDataModel>> getAllManga();

    Mono<byte[]> getChapter(String mangaName, String chapter);

    Mono<List<String>> getAllChapterNames(String mangaName);

    Mono<MangaDataModel> getDetails(String mangaName);
}
