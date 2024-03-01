package pl.coderslab.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.model.OmdbMovieDetails;

@Service
public class OmdbApiService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OmdbMovieDetails fetchMovieDetails(String imdbId) {
        String apiKey = "d877445b";
        String url = String.format("http://www.omdbapi.com/?i=%s&apikey=%s", imdbId, apiKey);
        String response = restTemplate.getForObject(url, String.class);
        try {
            return objectMapper.readValue(response, OmdbMovieDetails.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
