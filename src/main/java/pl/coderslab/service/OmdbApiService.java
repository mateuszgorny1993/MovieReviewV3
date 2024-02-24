package pl.coderslab.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.model.OmdbMovieDetails;

@Service
public class OmdbApiService {
    private final RestTemplate restTemplate = new RestTemplate();

    public OmdbMovieDetails fetchMovieDetails(String imdbId) {
        String apiKey = "d877445b";
        String url = String.format("http://www.omdbapi.com/?i=%s&apikey=%s", imdbId, apiKey);
        return restTemplate.getForObject(url, OmdbMovieDetails.class);
    }
}

