package cl.biblioteca.otaku.bff.controllers;

import cl.biblioteca.otaku.bff.models.FavoriteModel;
import cl.biblioteca.otaku.bff.models.FavoritePostRequest;
import cl.biblioteca.otaku.bff.service.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

     @PostMapping("/validateUser")
     public Mono<Boolean> validateUser(@RequestBody String email){
         return userService.validateOrCreateUser(email);
     }

     @GetMapping("/getFavorites")
    public Mono<List<FavoriteModel>> getFavorites(
            @RequestParam String email
     ){
        return userService.getFavorites(email);
     }

     @PostMapping("/addFavorite")
     public Mono<Boolean> addFavorite(
             @RequestBody FavoritePostRequest favoritePostRequest
     ){
         return userService.addFavorite(favoritePostRequest);
     }

     @DeleteMapping("/removeFavorite")
     public Mono<Boolean> removeFavorite(
             @RequestParam String email,
             @RequestParam String title
     ){
         return userService.removeFavorite(email, title);
     }

}
