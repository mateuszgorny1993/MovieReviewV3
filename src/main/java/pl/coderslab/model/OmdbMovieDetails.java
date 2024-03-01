package pl.coderslab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class OmdbMovieDetails {
    @JsonProperty("Title")
    private String title;

    @JsonProperty("Director")
    private String director;

    @JsonProperty("Genre")
    private String categories;

    @JsonProperty("Plot")
    private String description;

    @JsonProperty("Released")
    private String releaseDate;

    @JsonProperty("Runtime")
    private String duration;

    @JsonProperty("imdbRating")
    private String ocenaOmdb;

    @JsonProperty("Poster")
    private String posterPath;

    @JsonProperty("Actors")
    private String actors;
    @JsonProperty("imdbID")
    private String imdbId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getOcenaOmdb() {
        return ocenaOmdb;
    }

    public void setOcenaOmdb(String ocenaOmdb) {
        this.ocenaOmdb = ocenaOmdb;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }
}
