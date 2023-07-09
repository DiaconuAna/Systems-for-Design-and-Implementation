package catalog.web.converter;


import catalog.core.model.Movie;
import catalog.web.dto.MovieDto;
import org.springframework.stereotype.Component;

@Component
public class MovieConverter extends BaseConverter<Movie, MovieDto> {

    @Override
    public Movie convertDtoToModel(MovieDto dto) {
        var model = new Movie();
        model.setId(dto.getId());
        model.setTitle(dto.getTitle());
        model.setDirector(dto.getDirector());
        model.setGenreId(dto.getGenreId());
        model.setProductionCountry(dto.getProductionCountry());
        return model;
    }

    @Override
    public MovieDto convertModelToDto(Movie movie) {
        MovieDto dto = new MovieDto(movie.getTitle(), movie.getDirector(), movie.getGenreId(), movie.getProductionCountry());
        dto.setId(movie.getId());
        return dto;
    }
}
