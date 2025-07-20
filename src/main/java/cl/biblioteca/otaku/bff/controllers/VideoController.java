package cl.biblioteca.otaku.bff.controllers;

import cl.biblioteca.otaku.bff.models.SeriesDataModel;
import cl.biblioteca.otaku.bff.service.VideoService;
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

    // Endpoint to load all available series
    // returns a List<String> with the names of the series available.

    @GetMapping("/getListedSeries")
    public Mono<List<String>> getAllSeries(){
        return videoService.getAllSeries();
    }

    // Method to load Highlighted media
    // 12 series
    // 12 manga
    // 12 novels
    @GetMapping("/getHighlightedMedia")
    public Mono<List<SeriesDataModel>> getHighlightedMedia() {
        return videoService.getHighlightedMedia();
    };


    // Method to get the details of one specific series
    // we need the name of the series in question
    @GetMapping("/getDetails/{seriesName}")
    public Mono<SeriesDataModel> getDetails(@PathVariable String seriesName){
        return videoService.getDetails(seriesName);
    }


    @GetMapping("/getVideo/{folderName}/{fileName}")
    public Mono<ResponseEntity<Flux<DataBuffer>>> loadVideo(
            @PathVariable String fileName,
            @PathVariable String folderName,
            @RequestHeader(value = HttpHeaders.RANGE, required = false) String rangeHeader
            ) {
        return videoService.getVideo(folderName, fileName, rangeHeader);
    }

    @GetMapping("/getAllVideos")
    public Mono<List<String>> getAllVideos(){
        return videoService.getAllVideos();
    }

    @GetMapping("/getseriesData")
    public Mono<List<SeriesDataModel>> getSeriesData(){
        return videoService.getSeriesData();
    }
}
