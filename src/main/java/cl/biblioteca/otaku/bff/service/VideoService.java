package cl.biblioteca.otaku.bff.service;

import cl.biblioteca.otaku.bff.models.SeriesDataModel;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface VideoService {

    Mono<ResponseEntity<Flux<DataBuffer>>> getVideo(String folderName, String fileName, String rangeHeader);

    Mono<List<String>> getAllVideos();

    Mono<List<String>> getAllSeries();

    Mono<List<SeriesDataModel>> getSeriesData();

    Mono<List<SeriesDataModel>> getHighlightedMedia();

    Mono<SeriesDataModel> getDetails(String seriesName);

}
