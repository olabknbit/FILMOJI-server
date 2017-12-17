package pl.edu.agh.pp.api.responses;


public class UserResponse {
    private long id;
    private String username;
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public long getId() {
        return id;
    }

    public UserResponse() {}

    public UserResponse(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
