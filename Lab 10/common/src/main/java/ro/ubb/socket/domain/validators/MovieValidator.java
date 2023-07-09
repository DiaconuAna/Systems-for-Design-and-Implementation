package ro.ubb.socket.domain.validators;

import ro.ubb.socket.domain.Movie;

public class MovieValidator implements Validator<Movie> {
    @Override
    public void validate(Movie entity) throws ValidatorException {
        if (entity.getTitle().equals("")){
            throw new ValidatorException("The title of the movie is an empty string.");
        }
    }
}
