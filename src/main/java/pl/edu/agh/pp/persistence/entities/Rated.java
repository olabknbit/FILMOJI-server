package pl.edu.agh.pp.persistence.entities;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "RATED")
public class Rated {
    @StartNode
    User user;
    @EndNode
    Movie movie;
    @GraphId
    Long id;
    @Property
    Double rating;
    @Property
    Long timestamp;

    public Rated() {

    }

    public Rated(Long id, User user, Movie movie, Double rating, Long timestamp) {
        this.user = user;
        this.movie = movie;
        this.rating = rating;
        this.id = id;
        this.timestamp = timestamp;
    }

    public Rated(User user, Movie movie, Double rating, Long timestamp) {
        this.user = user;
        this.movie = movie;
        this.rating = rating;
        this.timestamp = timestamp;
    }


    public User getUser() {
        return user;
    }

    public Movie getMovie() {
        return movie;
    }

    public Double getRating() {
        return rating;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
