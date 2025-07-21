package cl.biblioteca.otaku.bff.service;

import cl.biblioteca.otaku.bff.models.FavoriteModel;
import cl.biblioteca.otaku.bff.models.FavoritePostRequest;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserService {

    Mono<Boolean> validateOrCreateUser(String email);

    Mono<List<FavoriteModel>> getFavorites(String email);

    Mono<Boolean> addFavorite(FavoritePostRequest favoritePostRequest);

    Mono<Boolean> removeFavorite(String email, String title);
}
