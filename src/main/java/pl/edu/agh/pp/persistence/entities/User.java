package pl.edu.agh.pp.persistence.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.Set;

@NodeEntity(label = "USER")
public class User {
    @GraphId
    private Long id_;
    private Long id;
    private String username;

    public User(Long id, String username, Set<Movie> ratedMovies) {
        this.id = id;
        this.username = username;
    }

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Rated userRatedMovie(Movie movie, Double rating) {
        Rated rated = new Rated(this, movie, rating, 0l);
        return rated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
