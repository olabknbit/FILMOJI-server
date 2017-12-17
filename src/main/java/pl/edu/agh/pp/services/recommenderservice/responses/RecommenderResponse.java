package pl.edu.agh.pp.services.recommenderservice.responses;

import java.util.List;

public class RecommenderResponse {
    private Double movieMatch;
    private Long movieId;
    private List<String> matchedTags;

    public RecommenderResponse() {
    }

    public RecommenderResponse(Double movieMatch, Long movieId, List<String> matchedTags) {
        this.movieMatch = movieMatch;
        this.movieId = movieId;
        this.matchedTags = matchedTags;
    }

    public Double getMovieMatch() {
        return movieMatch;
    }

    public Long getMovieId() {
        return movieId;
    }

    public List<String> getMatchedTags() {
        return matchedTags;
    }
}
