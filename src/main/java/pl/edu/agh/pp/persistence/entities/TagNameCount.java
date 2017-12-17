package pl.edu.agh.pp.persistence.entities;

import org.neo4j.ogm.annotation.*;

public class TagNameCount {
    @Property
    String tag;
    @Property
    Long count;

    public TagNameCount() {

    }

    public TagNameCount(String tag, Long count) {
        this.tag = tag;
        this.count = count;
    }

    public String getTag() {
        return tag;
    }

    public Long getCount() {
        return count;
    }
}
