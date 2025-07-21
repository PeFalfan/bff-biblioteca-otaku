package cl.biblioteca.otaku.bff.service.serviceImpl;

import cl.biblioteca.otaku.bff.models.FavoriteModel;
import cl.biblioteca.otaku.bff.models.FavoritePostRequest;
import cl.biblioteca.otaku.bff.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final WebClient webClient;

    @Autowired
    private ObjectMapper objectMapper;

    public UserServiceImpl(
            WebClient.Builder webBuilder,
            @Value("${user-service.url}") String url
    ) {
        this.webClient = webBuilder
                                 .codecs(configurer ->configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                                 .baseUrl(url)
                                 .build();
    }

    @Override
    public Mono<Boolean> validateOrCreateUser(String email) {
        return webClient.post()
                       .uri("/users/validateUser")
                       .bodyValue(email)
                       .retrieve()
                       .bodyToMono(Boolean.class)
                       .doOnError(e -> System.out.println("Error al validar el usuario " + email));
    }

    @Override
    public Mono<List<FavoriteModel>> getFavorites(
             String email
    ) {
        return webClient.get()
                       .uri(uriBuilder -> uriBuilder
                                                  .path("/users/getFavorites")
                                                  .queryParam("email", email)
                                                  .build())
                       .retrieve()
                       .bodyToMono(new ParameterizedTypeReference<List<FavoriteModel>>() {})
                       .doOnError(e -> System.out.println("Error al obtener favoritos del usuario " + email));
    }

    @Override
    public Mono<Boolean> addFavorite(FavoritePostRequest favoritePostRequest) {
        return webClient.post()
                       .uri("/users/addToFavorites")
                       .bodyValue(favoritePostRequest)
                       .retrieve()
                       .bodyToMono(Boolean.class)
                       .doOnError(e -> System.out.println("Error al agregar favorito " + favoritePostRequest));
    }

    @Override
    public Mono<Boolean> removeFavorite(String email, String title) {
        return webClient.delete()
                       .uri(uriBuilder -> uriBuilder
                                                  .path("/users/deleteFavorite")
                                                  .queryParam("email", email)
                                                  .queryParam("title", title)
                                                  .build())
                       .retrieve()
                       .bodyToMono(Boolean.class)
                       .doOnError(e -> System.out.println("Error al eliminar favorito " + email + " - " + title));
    }
}
