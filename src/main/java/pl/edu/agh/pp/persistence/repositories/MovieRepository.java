package pl.edu.agh.pp.persistence.repositories;

import com.sun.istack.internal.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.agh.pp.api.responses.CastMemberResponse;
import pl.edu.agh.pp.api.responses.MovieResponse;
import pl.edu.agh.pp.persistence.entities.ActedIn;
import pl.edu.agh.pp.persistence.entities.Movie;
import pl.edu.agh.pp.persistence.entities.Rated;

import java.util.*;

@Repository
public class MovieRepository {
    @Autowired
    Neo4jMoviesRepository repository;

    public List<Long> getMovieByTitle(String title, int quantity) {
        return repository.findByTitle(title, quantity);
    }

    public void rateMovie(Long movieId, Long userId, Double rating) {
        repository.rateMovie(userId, movieId, Math.max(0, Math.min(5, rating))); // rating should be between <0;5>.
    }

    public void tagMovie(Long movieId, Long userId, Set<String> tags) {
        for (Tag tag : Tag.values()) {
            if (tags.contains(tag.toString())) {
                repository.tagMovie(userId, movieId, tag.name());
            } else {
                repository.untagMovie(userId, movieId, tag.name());
            }
        }
    }

    public void addMovieToWatchlist(Long movieId, Long userId) {
        repository.addMovieToWatchlist(userId, movieId);
    }

    public void removeMovieFromWatchlist(Long movieId, Long userId) {
        repository.removeMovieFromWatchlist(userId, movieId);
    }

    public void likeMovie(Long movieId, Long userId) {
        repository.likeMovie(userId, movieId);
    }

    public void unlikeMovie(Long movieId, Long userId) {
        repository.unlikeMovie(userId, movieId);
    }

    public List<Long> getMoviesRatedByUser(Long userId, int quantity) {
        return repository.findMoviesRatedByUser(userId, quantity);
    }

    public List<Long> getUnratedMovies(Long userId, int quantity) {
        return repository.findUnratedMovies(userId, quantity);
    }

    public List<Long> getWatchlistedMovies(Long userId, int quantity) {
        return repository.findWatchlistedMovies(userId, quantity);
    }

    public List<Long> getLikedMovies(Long userId, int quantity) {
        return repository.findLikedMovies(userId, quantity);
    }

    public MovieResponse getMovie(Long movieId, Long userId, @Nullable Double match,
                                  @Nullable List<String> matchedTags) {
        Movie movie = repository.getMovieById(movieId);

        Iterator<Map<String, Object>> tagsIterator = repository.getMostPopularTagsForMovie(movieId, 3).iterator();
        List<String> tags = new ArrayList<>();
        while (tagsIterator.hasNext()) {
            tags.add((String) tagsIterator.next().get("NAME"));
        }
        Rated rated = repository.getMovieRatingByUser(userId, movieId);
        boolean liked = repository.isMovieLikedByUser(userId, movieId);
        boolean addedToWatchList = repository.isMovieAddedToWatchlistByUser(movieId, userId);
        List<String> tagged = repository.getMovieTagsByUser(userId, movieId);

        return new MovieResponse(movie, repository.getMovieGenres(movieId),
                converterPersonToCastMember(repository.getMovieCrew(movieId)), rated == null ? null : rated.getRating(),
                rated == null ? null : rated.getTimestamp(), addedToWatchList, liked, match, matchedTags, tags, tagged);
    }

    private List<CastMemberResponse> converterPersonToCastMember(List<ActedIn> movieCrew) {
        List<CastMemberResponse> cast = new ArrayList<>();
        movieCrew.stream()
                .sorted(Comparator.comparingInt(ActedIn::getOrder))
                .forEach(r -> cast.add(new CastMemberResponse(r)));
        return cast;
    }

    public enum Tag {
        intriguing,
        intense,
        thought_provoking,
        clever,
        inspiring,
        heart_warming,
        emotionally_investing,
        fun_to_watch,
        hard_to_follow,
        artistic,
        cliche_ridden,
        disappointing,
        underrated,
        waste_of_time,
        family_friendly,
        highly_original,
        memorable,
        easily_predictable,
        slow_paced,
        filled_with_action,
        insane,
        visually_stunning,
        scary_to_death,
        brutal
    }
}
