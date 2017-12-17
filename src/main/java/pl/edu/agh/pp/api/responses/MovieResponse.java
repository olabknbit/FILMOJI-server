package pl.edu.agh.pp.api.responses;

import pl.edu.agh.pp.persistence.entities.Movie;

import java.util.List;

public class MovieResponse {
    private Long id;
    private Long imdbId;
    private String title;
    private String originalTitle;
    private int year;
    private String posterPath;
    private String description;
    private double averageCount;
    private Long voteCount;
    private List<String> genres;
    private List<CastMemberResponse> crew;
    private Double rating;
    private Long ratingTimestamp;
    private boolean addedToWatchList;
    private boolean liked;
    private Double match;
    private List<String> matchedTags;
    private List<String> tagged;
    private List<String> tags;

    public MovieResponse() {

    }

    public MovieResponse(Movie movie, List<String> genres, List<CastMemberResponse> crew) {
        this.id = movie.getId();
        this.imdbId = movie.getImdbId();
        this.title = movie.getTitle();
        this.originalTitle = movie.getOriginalTitle();
        this.year = movie.getYear();
        this.posterPath = movie.getPoster();
        this.description = movie.getDescription();
        this.averageCount = movie.getVoteAverage() / 2;
        this.voteCount = movie.getVoteCount();
        this.genres = genres;
        this.crew = crew;
    }

    public MovieResponse(Movie movie, List<String> genres, List<CastMemberResponse> crew, Double rating,
                         Long ratingTimestamp, boolean addedToWatchList, boolean liked, Double match,
                         List<String> matchedTags, List<String> tags, List<String> tagged) {
        this(movie, genres, crew);
        this.rating = rating;
        this.ratingTimestamp = ratingTimestamp;
        this.addedToWatchList = addedToWatchList;
        this.liked = liked;
        this.match = match;
        this.matchedTags = matchedTags;
        this.tagged = tagged;
        this.tags = tags;
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

    public int getYear() {
        return year;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getDescription() {
        return description;
    }

    public double getAverageCount() {
        return averageCount;
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<CastMemberResponse> getCrew() {
        return crew;
    }

    public Double getRating() {
        return rating;
    }

    public Long getRatingTimestamp() {
        return ratingTimestamp;
    }

    public boolean isAddedToWatchList() {
        return addedToWatchList;
    }

    public boolean isLiked() {
        return liked;
    }

    public Double getMatch() {
        return match;
    }

    public List<String> getMatchedTags() {
        return matchedTags;
    }

    public List<String> getTagged() {
        return tagged;
    }

    public List<String> getTags() {
        return tags;
    }
}
