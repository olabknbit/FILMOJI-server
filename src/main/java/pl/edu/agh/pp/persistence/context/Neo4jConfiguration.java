package pl.edu.agh.pp.persistence.context;

import org.neo4j.ogm.service.Components;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "pl.edu.agh.pp.persistence.entities")
@EnableNeo4jRepositories(basePackages = "pl.edu.agh.pp.persistence.repositories")
@EnableTransactionManagement
public class Neo4jConfiguration {

    @Value("${NEO4J_PASSWORD}")
    private String password;

    @Value("${NEO4J_USERNAME}")
    private String username;

    @Value("${NEO4J_URL}")
    private String url;

    @Bean
    public SessionFactory sessionFactory() {
        org.neo4j.ogm.config.Configuration configuration = Components.getConfiguration();
        configuration.driverConfiguration()
                .setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
                .setCredentials(username, password)
                .setURI("http://" + url);
        return new SessionFactory(configuration, "pl.edu.agh.pp.persistence.entities");
    }


    @Bean
    public Neo4jTransactionManager transactionManager() {
        return new Neo4jTransactionManager(sessionFactory());
    }
}