package pl.edu.agh.pp.api.requests;

public class CreateUserRequest {
    private String username;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
