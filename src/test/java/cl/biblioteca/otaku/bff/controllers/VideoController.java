package cl.biblioteca.otaku.bff.controllers;

import cl.biblioteca.otaku.bff.models.SeriesDataModel;
import cl.biblioteca.otaku.bff.service.VideoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebFluxTest(VideoController.class)
@Import(VideoControllerTest.MockConfig.class)
class VideoControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private VideoService videoService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public VideoService videoService() {
            return Mockito.mock(VideoService.class);
        }
    }

    @Test
    void shouldReturnAllSeriesNames() {
        when(videoService.getAllSeries())
                .thenReturn(Mono.just(List.of("One Piece", "Attack on Titan")));

        webTestClient.get()
                .uri("/api/getListedSeries")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0]").isEqualTo("One Piece")
                .jsonPath("$[1]").isEqualTo("Attack on Titan");
    }

    @Test
    void shouldReturnHighlightedMedia() {
        SeriesDataModel series = new SeriesDataModel();
        series.setTitle("Fullmetal Alchemist");

        when(videoService.getHighlightedMedia())
                .thenReturn(Mono.just(List.of(series)));

        webTestClient.get()
                .uri("/api/getHighlightedMedia")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].title").isEqualTo("Fullmetal Alchemist");
    }

    @Test
    void shouldReturnSeriesDetailsByName() {
        SeriesDataModel series = new SeriesDataModel();
        series.setId(1L);
        series.setTitle("Naruto");
        series.setCurrentChapters(50);
        series.setTotalChapters(100);
        series.setMainTag("Shonen");
        series.setAllTags(new String[]{"Acción", "Aventura"});
        series.setOriginalName("ナルト");
        series.setDescription("Historia de ninjas");
        series.setChapters(new ArrayList<>());
        series.setMainImageUrl("naruto.jpg");
        series.setYearOfRelease(2002);

        when(videoService.getDetails("Naruto"))
                .thenReturn(Mono.just(series));

        webTestClient.get()
                .uri("/api/getDetails/Naruto") // <-- asegúrate que el controller tenga el slash
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.title").isEqualTo("Naruto")
                .jsonPath("$.currentChapters").isEqualTo(50)
                .jsonPath("$.totalChapters").isEqualTo(100)
                .jsonPath("$.mainTag").isEqualTo("Shonen")
                .jsonPath("$.allTags[0]").isEqualTo("Acción")
                .jsonPath("$.allTags[1]").isEqualTo("Aventura")
                .jsonPath("$.originalName").isEqualTo("ナルト")
                .jsonPath("$.description").isEqualTo("Historia de ninjas")
                .jsonPath("$.mainImageUrl").isEqualTo("naruto.jpg")
                .jsonPath("$.yearOfRelease").isEqualTo(2002)
                .jsonPath("$.chapters").isArray()
                .jsonPath("$.chapters.length()").isEqualTo(0);
    }

    @Test
    void shouldReturnAllVideos() {
        when(videoService.getAllVideos())
                .thenReturn(Mono.just(List.of("video1.mp4", "video2.mp4")));

        webTestClient.get()
                .uri("/api/getAllVideos")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0]").isEqualTo("video1.mp4")
                .jsonPath("$[1]").isEqualTo("video2.mp4");
    }

    @Test
    void shouldReturnSeriesData() {
        SeriesDataModel series = new SeriesDataModel();
        series.setTitle("Demon Slayer");

        when(videoService.getSeriesData())
                .thenReturn(Mono.just(List.of(series)));

        webTestClient.get()
                .uri("/api/getseriesData")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].title").isEqualTo("Demon Slayer");
    }

    @Test
    void shouldStubLoadVideo() {
        when(videoService.getVideo(eq("anime"), eq("ep1.mp4"), any()))
                .thenReturn(Mono.just(ResponseEntity.ok(Flux.empty())));

        webTestClient.get()
                .uri("/api/getVideo/anime/ep1.mp4")
                .header(HttpHeaders.RANGE, "bytes=0-")
                .exchange()
                .expectStatus().isOk();
    }
}
