package pl.coderslab.service;

import org.springframework.stereotype.Service;
import pl.coderslab.model.Trailer;
import pl.coderslab.repository.TrailerRepository;
import java.util.List;

@Service
public class TrailerService {

    private final TrailerRepository trailerRepository;

    public TrailerService(TrailerRepository trailerRepository) {
        this.trailerRepository = trailerRepository;
    }

    public List<Trailer> findTrailerByMovieId(Long movieId) {
        return trailerRepository.findByMovieId(movieId);
    }

    // Inne metody serwisowe
}
