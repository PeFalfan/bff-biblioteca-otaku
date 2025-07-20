package cl.biblioteca.otaku.bff.service.serviceImpl;

import cl.biblioteca.otaku.bff.models.MangaDataModel;
import cl.biblioteca.otaku.bff.service.MangaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class MangaServiceImpl implements MangaService {

    private final WebClient webClient;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(MangaServiceImpl.class);

    public MangaServiceImpl(
            WebClient.Builder webBuilder,
            @Value("${manga-service.url}") String url
    ) {
        this.webClient = webBuilder
                                 .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                                 .baseUrl(url)
                                 .build();
    }

    @Override
    public Mono<List<MangaDataModel>> getAllManga() {
        logger.info("Obteniendo datos de las mangas disponibles");

        return webClient.get()
                       .uri("/api/mangas/getMangas")
                       .retrieve()
                       .bodyToMono(new ParameterizedTypeReference<List<MangaDataModel>>() {})
                       .doOnError(e -> logger.error("Error al obtener la lista de mangas", e));
    }

    @Override
    public Mono<byte[]> getChapter(String mangaName, String chapter) {
        logger.info("Obteniendo el capítulo {} del manga {}", chapter, mangaName);
        return webClient.get()
                       .uri("/api/mangas/getChapter/"+ mangaName+"/" + chapter)
                       .retrieve()
                       .bodyToMono(byte[].class)
                       .doOnError(e -> logger.error("Error al obtener los datos del capítulo {} del manga {}", chapter, mangaName, e));
    }

    @Override
    public Mono<List<String>> getAllChapterNames(String mangaName) {
        logger.info("Obteniendo capítulos del manga {}", mangaName);
        return webClient.get()
                       .uri("/api/mangas/getChapters/"+ mangaName)
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
                       .doOnError(e -> logger.error("Error al deserializar la respuesta", e));
    }

    @Override
    public Mono<MangaDataModel> getDetails(String mangaName) {
        logger.info("Obteniendo datos de la manga {}", mangaName);
        return webClient.get()
                       .uri("/api/mangas/getManga/" + mangaName)
                       .retrieve()
                       .bodyToMono(MangaDataModel.class)
                       .doOnError(e -> logger.error("Error al obtener detalles de manga", e));
    }
}
