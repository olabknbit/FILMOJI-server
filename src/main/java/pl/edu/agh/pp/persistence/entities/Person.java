package pl.edu.agh.pp.persistence.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "PERSON")
public class Person {
    @GraphId
    private Long id_;
    @Property(name = "id")
    private Long id;
    @Property
    private String poster;
    @Property
    private String name;

    public Person() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPoster () {
        return poster;
    }
}
