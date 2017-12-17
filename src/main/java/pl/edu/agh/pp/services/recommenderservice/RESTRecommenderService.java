package pl.edu.agh.pp.services.recommenderservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.pp.services.recommenderservice.responses.RecommenderResponse;
import pl.edu.agh.pp.utils.Utils;

import java.util.List;
import java.util.Objects;

@Component
public class RESTRecommenderService {

    @Value("${RECOMMENDER_HOST}")
    private String host;

    @Value("${RECOMMENDER_PORT}")
    private String port;

    private RestTemplate restTemplate;

    private static HttpEntity<String> emptyBody = new HttpEntity<String>("");

    public RESTRecommenderService() {
        this.restTemplate = new RestTemplate();
    }

    public List<RecommenderResponse> getRecommendations(Long userId, int quantity) {
        ResponseEntity<List<RecommenderResponse>> response = restTemplate.exchange(
                host + ":" + port + "/users/" + userId + "/recommend?quantity=" + quantity,
                HttpMethod.GET, emptyBody,
                new ParameterizedTypeReference<List<RecommenderResponse>>() {
                });
        return response.getBody();
    }

    public List<RecommenderResponse> getRecommendations(Long userId, String label, int quantity) {
        ResponseEntity<List<RecommenderResponse>> response = restTemplate.exchange(
                host + ":" + port + "/users/" + userId + "/recommend?quantity=" + quantity + "&label=" + label,
                HttpMethod.GET, emptyBody,
                new ParameterizedTypeReference<List<RecommenderResponse>>() {
                });
        return response.getBody();
    }

    public void recomputeRecommendationsModel() {
        ResponseEntity<String> response =
                restTemplate.exchange(host + ":" + port + "/recompute", HttpMethod.PUT, emptyBody, String.class);
        System.out.println(response.toString());
    }

    public List<RecommenderResponse> getMatches(Long userId, List<Long> movieIds) {
        ResponseEntity<List<RecommenderResponse>> response = restTemplate.exchange(
                host + ":" + port + "/users/" + userId + "/match?movies=" + Utils.longsToString(movieIds),
                HttpMethod.GET, emptyBody,
                new ParameterizedTypeReference<List<RecommenderResponse>>() {
                });
        List<RecommenderResponse> responses = response.getBody();

        for (Long movieId : movieIds) {
            boolean has = false;
            for (RecommenderResponse recommenderResponse : responses) {
                if (Objects.equals(recommenderResponse.getMovieId(), movieId)) {
                    has = true;
                    break;
                }
            }
            if (!has) {
                responses.add(new RecommenderResponse(null, movieId, null));
            }
        }
        return responses;
    }
}
