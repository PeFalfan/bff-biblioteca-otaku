package cl.biblioteca.otaku.bff.controllers;

import cl.biblioteca.otaku.bff.models.MangaDataModel;
import cl.biblioteca.otaku.bff.service.MangaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@WebFluxTest(MangaController.class)
@Import(MangaControllerTest.MockConfig.class)
class MangaControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private MangaService mangaService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public MangaService mangaService() {
            return Mockito.mock(MangaService.class);
        }
    }

    @Test
    void shouldReturnSingleMangaByName() {
        MangaDataModel manga = new MangaDataModel(
                1L,
                "Naruto",
                700,
                "shonen",
                "Historia de un ninja",
                new ArrayList<>(),
                "naruto.jpg",
                1999
        );

        when(mangaService.getDetails("Naruto")).thenReturn(Mono.just(manga));

        webTestClient.get()
                .uri("/api/mangas/getManga/Naruto")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.title").isEqualTo("Naruto")
                .jsonPath("$.currentChapters").isEqualTo(700)
                .jsonPath("$.mainTag").isEqualTo("shonen")
                .jsonPath("$.description").isEqualTo("Historia de un ninja")
                .jsonPath("$.coverUrl").isEqualTo("naruto.jpg")
                .jsonPath("$.yearOfRelease").isEqualTo(1999)
                .jsonPath("$.chapters").isArray()
                .jsonPath("$.chapters.length()").isEqualTo(0);
    }

    @Test
    void shouldReturnChaptersForManga() {
        when(mangaService.getAllChapterNames("Naruto"))
                .thenReturn(Mono.just(List.of("Capítulo 1", "Capítulo 2")));

        webTestClient.get()
                .uri("/api/mangas/getChapters/Naruto")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0]").isEqualTo("Capítulo 1")
                .jsonPath("$[1]").isEqualTo("Capítulo 2");
    }
}
