package pl.edu.agh.pp.persistence.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "MOVIE")
public class Movie {
    @GraphId
    private Long id_;
    @Property(name = "id")
    private Long id;
    @Property(name = "imdb_id")
    private Long imdbId;
    @Property(name = "title")
    private String title;
    @Property(name = "original_title")
    private String originalTitle;
    @Property(name = "year")
    private Integer year;
    @Property(name = "vote_average")
    private Double voteAverage;
    @Property(name = "description")
    private String description;
    @Property(name = "poster")
    private String poster;
    @Property(name = "vote_count")
    private Long voteCount;

    public Movie() {
    }

    public Long getId() {
        return id;
    }

    public Long getImdbId() {
        return imdbId;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public Integer getYear() {
        return year;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getDescription() {
        return description;
    }

    public String getPoster() {
        return poster;
    }

    public Long getVoteCount() {
        return voteCount;
    }
}
