package pl.edu.agh.pp.api.responses;

import pl.edu.agh.pp.persistence.entities.ActedIn;

public class CastMemberResponse {
    private Long id;
    private String character;
    private String name;
    private int order;
    private String poster;

    public CastMemberResponse() {
    }

    public CastMemberResponse(ActedIn person) {
        this.id = person.getId();
        this.character = person.getCharacter();
        this.name = person.getPerson().getName();
        this.order = person.getOrder();
        this.poster = person.getPerson().getPoster();
    }

    public Long getId() {
        return id;
    }

    public String getCharacter() {
        return character;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public String getPoster() {
        return poster;
    }
}
