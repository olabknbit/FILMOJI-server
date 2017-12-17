package pl.edu.agh.pp.api;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.pp.api.requests.RatingRequest;
import pl.edu.agh.pp.api.responses.MovieResponse;
import pl.edu.agh.pp.api.responses.MoviesResponse;
import pl.edu.agh.pp.persistence.repositories.MovieRepository;
import pl.edu.agh.pp.services.recommenderservice.RESTRecommenderService;
import pl.edu.agh.pp.services.recommenderservice.responses.RecommenderResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class MoviesController {

    private final MovieRepository repository;

    private final RESTRecommenderService recommenderService;


    @Autowired
    public MoviesController(MovieRepository repository, RESTRecommenderService recommenderService) {
        this.repository = repository;
        this.recommenderService = recommenderService;
    }

/**********************************************************************************************************************
 * PUT requests. ******************************************************************************************************
 *********************************************************************************************************************/

    @RequestMapping(value = "/movies/{movieId}/rate", method = RequestMethod.PUT)
    public void rateMovie(@PathVariable Long movieId, @RequestBody RatingRequest request) {
        repository.rateMovie(movieId, request.getUserId(), request.getRating());
        repository.removeMovieFromWatchlist(movieId, request.getUserId());
        repository.tagMovie(movieId, request.getUserId(), request.getTagged());
    }

    @RequestMapping(value = "/movies/{movieId}/watchlist", method = RequestMethod.PUT)
    public void addMovieToWatchlist(@PathVariable Long movieId, @RequestParam Long userId) {
        repository.addMovieToWatchlist(movieId, userId);
    }

    @RequestMapping(value = "/movies/{movieId}/unwatchlist", method = RequestMethod.PUT)
    public void removeMovieFromWatchlist(@PathVariable Long movieId, @RequestParam Long userId) {
        repository.removeMovieFromWatchlist(movieId, userId);
    }

    @RequestMapping(value = "/movies/{movieId}/like", method = RequestMethod.PUT)
    public void likeMovie(@PathVariable Long movieId, @RequestParam Long userId) {
        repository.likeMovie(movieId, userId);
        repository.removeMovieFromWatchlist(movieId, userId);
    }

    @RequestMapping(value = "/movies/{movieId}/unlike", method = RequestMethod.PUT)
    public void unlikeMovie(@PathVariable Long movieId, @RequestParam Long userId) {
        repository.unlikeMovie(movieId, userId);
    }

    @RequestMapping(value = "/movies/recompute", method = RequestMethod.PUT)
    public void recomputeRecommendationsModel() throws IOException {
        recommenderService.recomputeRecommendationsModel();
    }

/**********************************************************************************************************************
 * GET requests. ******************************************************************************************************
 *********************************************************************************************************************/

    @RequestMapping(value = "/movies/{movieId}", method = RequestMethod.GET)
    public MovieResponse getMovie(@PathVariable Long movieId, @RequestParam Long userId) throws IOException {
        List<Long> movieIds = new ArrayList<>();
        movieIds.add(movieId);
        List<MovieResponse> movies = mapIdsToMovieResponses(movieIds, userId).getMovies();
        if (movies.size() > 0) {
            return movies.get(0);
        }
        return null;
    }

    @RequestMapping(value = "/movies/search", method = RequestMethod.GET)
    public MoviesResponse getMovie(@RequestParam String title, @RequestParam Long userId, @RequestParam Optional<Integer> quantity) throws IOException {
        return mapIdsToMovieResponses(repository.getMovieByTitle(title, quantity.orElse(10)), userId);
    }

    @RequestMapping(value = "/movies/liked", method = RequestMethod.GET)
    public MoviesResponse getLikedMovies(@RequestParam Long userId, @RequestParam Optional<Integer> quantity) throws IOException {
        return mapIdsToMovieResponses(repository.getLikedMovies(userId, quantity.orElse((100))), userId);
    }

    @RequestMapping(value = "/movies/watchlisted", method = RequestMethod.GET)
    public MoviesResponse getWatchlistedMovies(@RequestParam Long userId, @RequestParam Optional<Integer> quantity) throws IOException {
        return mapIdsToMovieResponses(repository.getWatchlistedMovies(userId, quantity.orElse((100))), userId);
    }

    @RequestMapping(value = "/movies/unrated", method = RequestMethod.GET)
    public MoviesResponse getUnratedMovies(@RequestParam Long userId, @RequestParam Optional<Integer> quantity) throws IOException {
        return mapIdsToMovieResponses(repository.getUnratedMovies(userId, quantity.orElse((20))), userId);
    }

    @RequestMapping(value = "/movies/recommended")
    public MoviesResponse getRecommendedMovies(@RequestParam Long userId, @RequestParam Optional<Integer> quantity) throws IOException {
        List<RecommenderResponse> recommendations = recommenderService.getRecommendations(userId, quantity.orElse((100)));
        return mapRecommenderResponseToMovieResponseWithMatch(recommendations, userId);
    }

    @RequestMapping(value = "/movies/lrecommended")
    public MoviesResponse getRecommendedMovies(@RequestParam Long userId, @RequestParam String label, @RequestParam Optional<Integer> quantity) throws IOException {
        if (EnumUtils.isValidEnum(MovieRepository.Tag.class, label)) {
            List<RecommenderResponse> recommendations = recommenderService.getRecommendations(userId, label, quantity.orElse((100)));
            return mapRecommenderResponseToMovieResponseWithMatch(recommendations, userId);
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/movies/rated")
    public MoviesResponse getRatedMovies(@RequestParam Long userId, @RequestParam Optional<Integer> quantity) throws IOException {
        return mapIdsToMovieResponses(repository.getMoviesRatedByUser(userId, quantity.orElse((100))), userId);
    }

    private MoviesResponse mapRecommenderResponseToMovieResponseWithMatch(List<RecommenderResponse> responses, Long userId) {
        List<MovieResponse> movieResponses = new ArrayList<>();
        for (RecommenderResponse response : responses) {
            MovieResponse movieResponse = repository.getMovie(response.getMovieId(), userId, response.getMovieMatch(), response.getMatchedTags());
            movieResponses.add(movieResponse);
        }
        return new MoviesResponse(movieResponses);
    }

    private MoviesResponse mapIdsToMovieResponses(List<Long> movieIds, Long userId) throws IOException {
        return mapRecommenderResponseToMovieResponseWithMatch(recommenderService.getMatches(userId, movieIds), userId);
    }
}
