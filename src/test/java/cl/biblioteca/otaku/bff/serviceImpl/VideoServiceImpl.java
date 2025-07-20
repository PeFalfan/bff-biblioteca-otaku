package cl.biblioteca.otaku.bff.serviceImpl;

import cl.biblioteca.otaku.bff.models.SeriesDataModel;
import cl.biblioteca.otaku.bff.service.serviceImpl.VideoServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.*;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class VideoServiceImplTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private RequestHeadersUriSpec<?> uriSpec;

    @Mock
    private RequestHeadersSpec<?> headersSpec;

    @Mock
    private ResponseSpec responseSpec;

    @Mock
    private ObjectMapper objectMapper;

    private VideoServiceImpl videoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(webClientBuilder.codecs(any())).thenReturn(webClientBuilder);
        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);

        String baseUrl = "http://mocked-url";
        videoService = new VideoServiceImpl(webClientBuilder, baseUrl);
        videoService.objectMapper = objectMapper; // inyectar mock
    }

    @Test
    void getAllSeries() throws Exception {
        String json = "[\"Naruto\",\"Bleach\"]";
        List<String> expected = List.of("Naruto", "Bleach");

        when(webClient.get()).thenAnswer(invocationOnMock -> uriSpec);
        when(uriSpec.uri("/api/videos/getAvailableSeries")).thenAnswer( invocationOnMock -> headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(json));
        when(objectMapper.readValue(eq(json), any(TypeReference.class)))
                .thenAnswer(invocation -> {
                    TypeReference<?> typeRef = invocation.getArgument(1);
                    if (typeRef.getType().getTypeName().contains("List<java.lang.String>")) {
                        return expected;
                    }
                    return null;
                });

        StepVerifier.create(videoService.getAllSeries())
                .expectNext(expected)
                .verifyComplete();
    }

    @Test
    void getAllVideos() throws Exception {
        String json = "[\"video1.mp4\",\"video2.mp4\"]";
        List<String> expected = List.of("video1.mp4", "video2.mp4");

        when(webClient.get()).thenAnswer(invocationOnMock -> uriSpec);
        when(uriSpec.uri("/api/videos/getAvailableVideos")).thenAnswer( invocationOnMock -> headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(json));
        when(objectMapper.readValue(eq(json), any(TypeReference.class)))
                .thenAnswer(invocation -> {
                    TypeReference<?> typeRef = invocation.getArgument(1);
                    if (typeRef.getType().getTypeName().contains("List<java.lang.String>")) {
                        return expected;
                    }
                    return null;
                });

        StepVerifier.create(videoService.getAllVideos())
                .expectNext(expected)
                .verifyComplete();
    }

    @Test
    void getDetails() throws Exception {
        String seriesName = "Naruto";
        String json = "{\"title\":\"Naruto\"}";
        SeriesDataModel expected = new SeriesDataModel();
        expected.setTitle("Naruto");

        when(webClient.get()).thenAnswer(invocationOnMock -> uriSpec);
        when(uriSpec.uri("/api/videos/getDetails" + seriesName)).thenAnswer( invocationOnMock -> headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(json));
        when(objectMapper.readValue(eq(json), any(TypeReference.class)))
                .thenAnswer(invocation -> {
                    TypeReference<?> typeRef = invocation.getArgument(1);
                    if (typeRef.getType().getTypeName().contains("SeriesDataModel")) {
                        return expected;
                    }
                    return null;
                });

        StepVerifier.create(videoService.getDetails(seriesName))
                .expectNextMatches(model -> model.getTitle().equals("Naruto"))
                .verifyComplete();
    }

    @Test
    void getSeriesData() throws Exception {
        String json = "[{\"title\":\"Naruto\"}]";
        SeriesDataModel model = new SeriesDataModel();
        model.setTitle("Naruto");
        List<SeriesDataModel> expected = List.of(model);

        when(webClient.get()).thenAnswer(invocationOnMock -> uriSpec);
        when(uriSpec.uri("/api/videos/getSeriesData")).thenAnswer( invocationOnMock -> headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(json));
        when(objectMapper.readValue(eq(json), any(TypeReference.class)))
                .thenAnswer(invocation -> {
                    TypeReference<?> typeRef = invocation.getArgument(1);
                    if (typeRef.getType().getTypeName().contains("List<cl.biblioteca.otaku.bff.models.SeriesDataModel>")) {
                        return expected;
                    }
                    return null;
                });

        StepVerifier.create(videoService.getSeriesData())
                .expectNext(expected)
                .verifyComplete();
    }

    @Test
    void getHighlightedMedia() throws Exception {
        String json = "[{\"title\":\"Bleach\"}]";
        SeriesDataModel model = new SeriesDataModel();
        model.setTitle("Bleach");
        List<SeriesDataModel> expected = List.of(model);

        when(webClient.get()).thenAnswer(invocationOnMock -> uriSpec);
        when(uriSpec.uri("/api/videos/getHighlightedMedia")).thenAnswer( invocationOnMock -> headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(json));
        when(objectMapper.readValue(eq(json), any(TypeReference.class)))
                .thenAnswer(invocation -> {
                    TypeReference<?> typeRef = invocation.getArgument(1);
                    if (typeRef.getType().getTypeName().contains("List<cl.biblioteca.otaku.bff.models.SeriesDataModel>")) {
                        return expected;
                    }
                    return null;
                });

        StepVerifier.create(videoService.getHighlightedMedia())
                .expectNext(expected)
                .verifyComplete();
    }
}
