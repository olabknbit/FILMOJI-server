package pl.edu.agh.pp.persistence.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.pp.persistence.entities.User;

import java.util.List;


public interface Neo4jUserRepository extends GraphRepository<User> {
    @Query("MATCH (user:USER) RETURN user.id + 1 ORDER BY user.id DESC LIMIT 1")
    Long getNewId();

    @Query("MATCH (user:USER{id:{userId}}) RETURN user")
    User getUser(@Param("userId") Long userId);

    @Query("MATCH (user:USER) RETURN user")
    List<User> getUsers();
}
