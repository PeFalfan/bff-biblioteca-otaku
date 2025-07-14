package cl.biblioteca.otaku.bff.controllers;

import cl.biblioteca.otaku.bff.models.MangaDataModel;
import cl.biblioteca.otaku.bff.service.MangaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/mangas")
public class MangaController {

    private final MangaService mangaService;

    public MangaController(MangaService mangaService) {
        this.mangaService = mangaService;
    }

    @GetMapping("/getMangas")
    public Mono<List<MangaDataModel>> getMangas(){
        return mangaService.getAllManga();
    }

    @GetMapping("/getManga/{mangaName}")
    public Mono<MangaDataModel> getManga(
            @PathVariable String mangaName
    ) {
        return mangaService.getDetails(mangaName);
    }

    @GetMapping("/getChapters/{mangaName}")
    public Mono<List<String>> getChapters(
            @PathVariable String mangaName
    ) {
        return mangaService.getAllChapterNames(mangaName);
    }

    @GetMapping("/getChapter/{mangaName}/{chapter}")
    public Mono<byte[]> getChapter(
            @PathVariable String mangaName,
            @PathVariable String chapter
    ) {
        return mangaService.getChapter(mangaName, chapter);
    }
}
