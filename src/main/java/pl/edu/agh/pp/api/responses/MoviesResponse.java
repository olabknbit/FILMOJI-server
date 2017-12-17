package pl.edu.agh.pp.api.responses;

import java.util.List;

public class MoviesResponse {
    private List<MovieResponse> movies;

    public MoviesResponse() {
    }

    public MoviesResponse(List<MovieResponse> movies) {
        this.movies = movies;
    }

    public List<MovieResponse> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieResponse> movies) {
        this.movies = movies;
    }
}
