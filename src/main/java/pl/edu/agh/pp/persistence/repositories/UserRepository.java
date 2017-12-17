package pl.edu.agh.pp.persistence.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.agh.pp.persistence.entities.User;

import java.util.List;

@Repository
public class UserRepository {
    @Autowired
    Neo4jUserRepository repository;

    public void addUser(User user) {
        repository.save(user);
    }

    public List<User> getUsers() {
        return repository.getUsers();
    }

    public User getUser(Long id) {
        return repository.getUser(id);
    }

    public Long getNewId() {
        return repository.getNewId();
    }


}
