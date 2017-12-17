package pl.edu.agh.pp.persistence.entities;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "ACTED_IN")
public class ActedIn {
    @StartNode
    Person person;
    @EndNode
    Movie movie;
    @GraphId
    Long id;
    @Property
    String character;
    @Property
    Integer order;

    public ActedIn() {

    }

    public ActedIn(Person person, Movie movie, Long id, String character, Integer order) {
        this.person = person;
        this.movie = movie;
        this.id = id;
        this.character = character;
        this.order = order;
    }

    public Person getPerson() {
        return person;
    }

    public Movie getMovie() {
        return movie;
    }

    public Long getId() {
        return id;
    }

    public String getCharacter() {
        return character;
    }

    public Integer getOrder() {
        return order;
    }
}
