package pl.edu.agh.pp.persistence.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.pp.persistence.entities.ActedIn;
import pl.edu.agh.pp.persistence.entities.Movie;
import pl.edu.agh.pp.persistence.entities.Rated;

import java.util.List;
import java.util.Map;

public interface Neo4jMoviesRepository extends GraphRepository<Movie> {
    @Query("MATCH (movie:MOVIE) WHERE toLower(movie.title) CONTAINS toLower({title}) " +
            "RETURN movie.id ORDER BY movie.vote_count DESC LIMIT {quantity}")
    List<Long> findByTitle(@Param("title") String title, @Param("quantity") int quantity);

    @Query("MATCH (movie:MOVIE {id:{movieId}}) MATCH (user:USER{id:{userId}}) " +
            "MERGE (movie)<-[rated:RATED]-(user)" +
            "SET rated.rating={rating}" +
            "SET rated.timestamp=timestamp()")
    void rateMovie(@Param("userId") Long userId, @Param("movieId") Long movieId, @Param("rating") Double rating);

    @Query("MATCH (movie:MOVIE {id:{movieId}}) MATCH (user:USER{id:{userId}}) MERGE (movie)<-[:TAGGED {tag:{tag}}]-(user)")
    void tagMovie(@Param("userId") Long userId, @Param("movieId") Long movieId, @Param("tag") String tag);

    @Query("MATCH (movie:MOVIE {id:{movieId}})<-[r:TAGGED {tag:{tag}}]-(user:USER{id:{userId}}) DELETE r")
    void untagMovie(@Param("userId") Long userId, @Param("movieId") Long movieId, @Param("tag") String tag);

    @Query("MATCH (movie:MOVIE {id:{movieId}}) MATCH (user:USER{id:{userId}}) MERGE (movie)<-[:WATCHED]-(user)")
    void addMovieToWatchlist(@Param("userId") Long userId, @Param("movieId") Long movieId);

    @Query("MATCH (movie:MOVIE {id:{movieId}})<-[r:WATCHED]-(user:USER{id:{userId}}) DELETE r")
    void removeMovieFromWatchlist(@Param("userId") Long userId, @Param("movieId") Long movieId);

    @Query("MATCH (movie:MOVIE {id:{movieId}}) MATCH (user:USER{id:{userId}}) MERGE (movie)<-[:LIKED]-(user)")
    void likeMovie(@Param("userId") Long userId, @Param("movieId") Long movieId);

    @Query("MATCH (movie:MOVIE {id:{movieId}})<-[r:LIKED]-(user:USER{id:{userId}}) DELETE r")
    void unlikeMovie(@Param("userId") Long userId, @Param("movieId") Long movieId);

    @Query("MATCH (movie:MOVIE{id:{movieId}})<-[r:RATED]-(u:USER{id:{userId}}) RETURN r ORDER BY r.timestamp DESC LIMIT 1")
    Rated getMovieRatingByUser(@Param("userId") Long userId, @Param("movieId") Long movieId);

    @Query("MATCH (movie:MOVIE)<-[r:RATED]-(u:USER{id:{userId}}) RETURN movie.id LIMIT {quantity}")
    List<Long> findMoviesRatedByUser(@Param("userId") Long userId, @Param("quantity") int quantity);

    @Query("MATCH (u:USER{id:{userId}})-[r:TAGGED]->(m:MOVIE{id:{movieId}}) return r.tag")
    List<String> getMovieTagsByUser(@Param("userId") Long userId, @Param("movieId") Long movieId);

    @Query("MATCH (movie:MOVIE {id:{movieId}})<-[r:TAGGED]-(user:USER) " +
            "RETURN r.tag AS NAME, COUNT(r) AS NUM ORDER BY count(r) DESC LIMIT {quantity}")
    Iterable<Map<String, Object>> getMostPopularTagsForMovie(@Param("movieId") Long movieId, @Param("quantity") int quantity);

    @Query("MATCH (user:USER{id:{userId}}) MATCH (movie:MOVIE) " +
            "WHERE NOT (user)-[:RATED]->(movie) " +
            "AND NOT (user)-[:TAGGED]->(movie) " +
            "AND NOT (user)-[:LIKED]->(movie) " +
            "AND NOT (user)-[:WATCHED]->(movie) " +
            "RETURN movie.id ORDER BY movie.vote_count DESC LIMIT {quantity}")
    List<Long> findUnratedMovies(@Param("userId") Long userId, @Param("quantity") int quantity);

    @Query("MATCH (movie:MOVIE{id:{movieId}})<-[r:WATCHED]-(u:USER{id:{userId}}) return count(movie)>0")
    Boolean isMovieAddedToWatchlistByUser(@Param("movieId")Long movieId, @Param("userId") Long userId);

    @Query("MATCH (movie:MOVIE)<-[:LIKED]-(u:USER{id:{userId}}) RETURN movie.id LIMIT {quantity}")
    List<Long> findLikedMovies(@Param("userId") Long userId, @Param("quantity") int quantity);

    @Query("MATCH (movie:MOVIE)<-[:WATCHED]-(u:USER{id:{userId}}) RETURN movie.id LIMIT {quantity}")
    List<Long> findWatchlistedMovies(@Param("userId") Long userId, @Param("quantity") int quantity);

    @Query("MATCH (movie:MOVIE{id:{movieId}}) RETURN movie")
    Movie getMovieById(@Param("movieId") Long movieId);

    @Query("MATCH (movie:MOVIE{id:{movieId}})-[:OF]->(g:GENRE) RETURN g.name")
    List<String> getMovieGenres(@Param("movieId") Long movieId);

    @Query("MATCH (movie:MOVIE{id:{movieId}})<-[r:ACTED_IN]-(p:PERSON) RETURN r")
    List<ActedIn> getMovieCrew(@Param("movieId") Long movieId);

    @Query("MATCH (movie:MOVIE{id:{movieId}})<-[r:LIKED]-(u:USER{id:{userId}}) return count(movie)>0")
    Boolean isMovieLikedByUser(@Param("userId") Long userId, @Param("movieId") Long movieId);
}
