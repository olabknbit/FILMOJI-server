package pl.edu.agh.pp.api.responses;

import pl.edu.agh.pp.persistence.entities.User;

import java.util.List;

public class UsersResponse {
    private List<User> users;

    public UsersResponse() {
    }

    public UsersResponse(List<User> users) {
        this.users = users;

    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
