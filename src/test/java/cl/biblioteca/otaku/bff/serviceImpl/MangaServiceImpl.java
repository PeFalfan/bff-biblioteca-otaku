package cl.biblioteca.otaku.bff.serviceImpl;

import cl.biblioteca.otaku.bff.models.MangaDataModel;
import cl.biblioteca.otaku.bff.service.serviceImpl.MangaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.*;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MangaServiceImplTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private RequestHeadersUriSpec<?> requestHeadersUriSpec;

    @Mock
    private RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private ResponseSpec responseSpec;

    private MangaServiceImpl mangaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(webClientBuilder.codecs(any())).thenReturn(webClientBuilder);
        String baseUrl = "http://mocked-url";
        when(webClientBuilder.baseUrl(baseUrl)).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);

        mangaService = new MangaServiceImpl(webClientBuilder, baseUrl);
    }

    @Test
    void getAllManga_ShouldReturnList() {
        List<MangaDataModel> mockList = List.of(
                new MangaDataModel(1L, "Naruto", 700, "shonen", "desc", null, "url.jpg", 2002)
        );

        when(webClient.get()).thenAnswer(invocation -> requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/api/mangas/getMangas")).thenAnswer(invocationOnMock -> requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        when(responseSpec.bodyToMono(any(ParameterizedTypeReference.class))).thenReturn(Mono.just(mockList));

        StepVerifier.create(mangaService.getAllManga())
                .assertNext(list -> {
                    assert list.size() == 1;
                    MangaDataModel manga = list.getFirst();
                    assert manga.getTitle().equals("Naruto");
                    assert manga.getCurrentChapters() == 700;
                    assert manga.getMainTag().equals("shonen");
                    assert manga.getDescription().equals("desc");
                    assert manga.getCoverUrl().equals("url.jpg");
                    assert manga.getYearOfRelease() == 2002;
                })
                .verifyComplete();
    }

    @Test
    void getDetails_ShouldReturnMangaData() {
        MangaDataModel mockManga = new MangaDataModel(2L, "One Piece", 1000, "adventure", "desc", null, "url2.jpg", 1999);

        when(webClient.get()).thenAnswer(invocation -> requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/api/mangas/getManga/OnePiece")).thenAnswer(invocationOnMock -> requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        when(responseSpec.bodyToMono(MangaDataModel.class)).thenReturn(Mono.just(mockManga));

        StepVerifier.create(mangaService.getDetails("OnePiece"))
                .assertNext(manga -> {
                    assert manga.getId() == 2L;
                    assert manga.getTitle().equals("One Piece");
                    assert manga.getCurrentChapters() == 1000;
                    assert manga.getMainTag().equals("adventure");
                    assert manga.getDescription().equals("desc");
                    assert manga.getCoverUrl().equals("url2.jpg");
                    assert manga.getYearOfRelease() == 1999;
                })
                .verifyComplete();
    }
}
