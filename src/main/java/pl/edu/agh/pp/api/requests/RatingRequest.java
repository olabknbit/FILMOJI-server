package pl.edu.agh.pp.api.requests;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class RatingRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Double rating;

    @NotNull
    private Set<String> tagged;

    public RatingRequest() {}

    public RatingRequest(@NotNull Long userId, @NotNull Double rating, @NotNull Set<String> tagged) {
        this.userId = userId;
        this.rating = rating;
        this.tagged = tagged;
    }

    public Long getUserId() {
        return userId;
    }

    public Double getRating() {
        return rating;
    }

    public Set<String> getTagged() {
        return tagged;
    }
}
