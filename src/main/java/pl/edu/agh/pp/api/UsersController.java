package pl.edu.agh.pp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.pp.api.requests.CreateUserRequest;
import pl.edu.agh.pp.api.responses.UsersResponse;
import pl.edu.agh.pp.persistence.entities.User;
import pl.edu.agh.pp.persistence.repositories.MovieRepository;
import pl.edu.agh.pp.persistence.repositories.UserRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@RestController
public class UsersController {

    private final UserRepository repository;

    private final MovieRepository movieRepository;

    @Autowired
    public UsersController(UserRepository repository, MovieRepository movieRepository) {
        this.repository = repository;
        this.movieRepository = movieRepository;
    }


    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public Map<String, Long> createUser(@RequestBody CreateUserRequest request) {
        User user = new User(repository.getNewId(), request.getUsername(), new HashSet<>());
        repository.addUser(user);
        long id = user.getId();
        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return resultMap;
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long userId) {
        return repository.getUser(userId);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public UsersResponse getUsers(){
        return new UsersResponse(repository.getUsers());
    }

}
