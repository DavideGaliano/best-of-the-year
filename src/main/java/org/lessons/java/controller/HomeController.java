package org.lessons.java.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.lessons.java.model.Movie;
import org.lessons.java.model.Song;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("name", "Davide"); 
        return "home";
    }
    
    private List<Movie> getBestMovies() {
        return Arrays.asList(
            new Movie(1, "Marvel Avengers"),
            new Movie(2, "Il Padrino"),
            new Movie(3, "Batman The Dark Knight")
        );
    }

    private List<Song> getBestSongs() {
        return Arrays.asList(
            new Song(1, "Bohemian Rhapsody"),
            new Song(2, "Imagine"),
            new Song(3, "Stairway to Heaven")
        );
    }

    @GetMapping("/movies")
    public String movies(Model model) {
        List<String> movieTitles = getBestMovies().stream()
            .map(Movie::getTitle)
            .collect(Collectors.toList());
        model.addAttribute("titles", String.join(", ", movieTitles));
        return "movies";
    }

    @GetMapping("/songs")
    public String songs(Model model) {
        List<String> songTitles = getBestSongs().stream()
            .map(Song::getTitle)
            .collect(Collectors.toList());
        model.addAttribute("titles", String.join(", ", songTitles));
        return "songs";
    }
    
    @GetMapping("/movies/{id}")
    public String movieById(@PathVariable int id, Model model) {
        Optional<Movie> movie = getMovieById(id);
        if (movie.isPresent()) {
            model.addAttribute("title", movie.get().getTitle());
        } else {
            model.addAttribute("title", "Movie not found");
        }
        return "movie-detail";
    }

    @GetMapping("/songs/{id}")
    public String songById(@PathVariable int id, Model model) {
        Optional<Song> song = getSongById(id);
        if (song.isPresent()) {
            model.addAttribute("title", song.get().getTitle());
        } else {
            model.addAttribute("title", "Song not found");
        }
        return "song-detail";
    }

    private Optional<Movie> getMovieById(int id) {
        return getBestMovies().stream()
                .filter(movie -> movie.getId() == id)
                .findFirst();
    }

    private Optional<Song> getSongById(int id) {
        return getBestSongs().stream()
                .filter(song -> song.getId() == id)
                .findFirst();
    }
}