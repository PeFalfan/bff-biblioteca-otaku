package cl.biblioteca.otaku.bff.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface VideoService {

    Mono<ResponseEntity<Flux<DataBuffer>>> getVideo(String fileName, String rangeHeader);

    Mono<List<String>> getAllVideos();

    Mono<List<String>> getAllSeries();
}
