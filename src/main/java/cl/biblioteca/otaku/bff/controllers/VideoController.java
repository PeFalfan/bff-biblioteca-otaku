package cl.biblioteca.otaku.bff.controllers;

import cl.biblioteca.otaku.bff.service.VideoService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/getVideo/{fileName}")
    public Mono<ResponseEntity<Flux<DataBuffer>>> loadVideo(
            @PathVariable String fileName,
            @RequestHeader(value = HttpHeaders.RANGE, required = false) String rangeHeader
            ) {
        return videoService.getVideo(fileName, rangeHeader);
    }

    @GetMapping("/getAllVideos")
    public Mono<List<String>> getAllVideos(){
        return videoService.getAllVideos();
    }

    @GetMapping("/getListedSeries")
    public Mono<List<String>> getAllSeries(){
        return videoService.getAllSeries();
    }

    @GetMapping("/testService")
    public String testService(){
        return "testService from BFF";
    }
}
