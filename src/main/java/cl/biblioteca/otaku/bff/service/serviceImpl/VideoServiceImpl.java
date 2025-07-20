package cl.biblioteca.otaku.bff.service.serviceImpl;

import cl.biblioteca.otaku.bff.models.SeriesDataModel;
import cl.biblioteca.otaku.bff.service.VideoService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    private final WebClient webClient;

    @Autowired
    public ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);

    public VideoServiceImpl(
            WebClient.Builder webBuilder,
            @Value("${video-service.url}") String url
    ) {
        this.webClient = webBuilder
                                 .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                                 .baseUrl(url)
                                 .build();
    }

    // Method to load all available series
    // returns a List<String> with the names of the series available.

    @Override
    public Mono<List<String>> getAllSeries() {
        logger.info("Obteniendo series disponibles");
        return webClient.get()
                       .uri("/api/videos/getAvailableSeries")
                       .retrieve()
                       .bodyToMono(String.class)
                       .<List<String>>handle((json, sink) -> {
                           try {
                               sink.next(objectMapper.readValue(json, new TypeReference<>() {
                               }));
                           } catch (Exception e) {
                               sink.error(new RuntimeException("Error al deserializar la respuesta", e));
                           }
                       })
                       .doOnError(e -> logger.error("Error al obtener videos disponibles", e));
    }

    // Load the details for one particular series
    @Override
    public Mono<SeriesDataModel> getDetails(String seriesName) {
        logger.info("Obteniendo datos de la serie {}", seriesName);
        return webClient.get()
                       .uri("/api/videos/getDetails" + seriesName )
                       .retrieve()
                       .bodyToMono(String.class)
                       .<SeriesDataModel>handle((json, sink) -> {
                           try {
                               sink.next(objectMapper.readValue(json, new TypeReference<>() {
                               }));
                           } catch (Exception e) {
                               sink.error(new RuntimeException("Error al deserializar la respuesta", e));
                           }
                       })
                       .doOnError(e -> logger.error("Error al obtener datos de la serie {}", seriesName, e));
    }



    public Mono<ResponseEntity<Flux<DataBuffer>>> getVideo(String folderName, String fileName, String rangeHeader) {
        return webClient.get()
                       .uri("/api/videos/playVideo/{folderName}/{filename}", folderName, fileName)
                       .header(HttpHeaders.RANGE, rangeHeader != null ? rangeHeader : "")
                       .accept(MediaType.APPLICATION_OCTET_STREAM)
                       .retrieve()
                       .onStatus(
                               status -> status.is4xxClientError() || status.is5xxServerError(),
                               response -> response.bodyToMono(String.class)
                                                   .flatMap(
                                                           body ->
                                                                   Mono.error(
                                                                           new RuntimeException("Error desde servicio de video: " + response.statusCode() + " - " + body)
                                                       )
                               )
                       )
                       .toEntityFlux(DataBuffer.class)
                       .map(entity -> {
                           HttpHeaders headers = new HttpHeaders();
                           headers.addAll(entity.getHeaders());
                           return ResponseEntity
                                          .status(entity.getStatusCode())
                                          .headers(headers)
                                          .body(entity.getBody());
                       });
    }

    @Override
    public Mono<List<String>> getAllVideos() {

        logger.info("Obteniendo videos disponibles");

        return webClient.get()
                       .uri("/api/videos/getAvailableVideos")
                       .retrieve()
                       .bodyToMono(String.class)
                       .<List<String>>handle((json, sink) -> {
                           try {
                               sink.next(objectMapper.readValue(json, new TypeReference<>() {
                               }));
                           } catch (Exception e) {
                               sink.error(new RuntimeException("Error al deserializar la respuesta", e));
                           }
                       })
                       .doOnError(e -> logger.error("Error al obtener videos disponibles", e));
    }



    @Override
    public Mono<List<SeriesDataModel>> getSeriesData() {
        logger.info("Obteniendo datos de las series ...");
        return webClient.get()
                       .uri("/api/videos/getSeriesData")
                       .retrieve()
                       .bodyToMono(String.class)
                       .<List<SeriesDataModel>>handle((json, sink) -> {
                           try {
                               sink.next(objectMapper.readValue(json, new TypeReference<>() {
                               }));
                           } catch (Exception e) {
                               sink.error(new RuntimeException("Error al deserializar la respuesta", e));
                           }
                       })
                       .doOnError(e -> logger.error("Error al obtener videos disponibles", e));
    }

    @Override
    public Mono<List<SeriesDataModel>> getHighlightedMedia() {
        logger.info("Obteniendo datos de series destacadas ...");
        return webClient.get()
                       .uri("/api/videos/getHighlightedMedia")
                       .retrieve()
                       .bodyToMono(String.class)
                       .<List<SeriesDataModel>>handle((json, sink) -> {
                           try {
                               logger.info("json: {}", json);
                               sink.next(objectMapper.readValue(json, new TypeReference<>() {
                               }));
                           } catch (Exception e) {
                               sink.error(new RuntimeException("Error al deserializar la respuesta", e));
                           }
                       })
                       .doOnError(e -> logger.error("Error al obtener videos disponibles", e));
    }



}
