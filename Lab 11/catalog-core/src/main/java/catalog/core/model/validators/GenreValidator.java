package catalog.core.model.validators;

import catalog.core.model.Genre;

public class GenreValidator implements Validator<Genre> {
    @Override
    public void validate(Genre entity) throws ValidatorException {
        if (entity.getName().equals("")) throw new ValidatorException("Genre name cannot be empty string");
        if (entity.getDescription().equals("")) throw new ValidatorException("Genre description cannot be empty string");
        if (entity.getId() <= 0) throw new ValidatorException("Genre id has to be a positive number");
    }
}
