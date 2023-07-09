package catalog.web.converter;

import catalog.core.model.MovieActor;
import catalog.web.dto.MovieActorDto;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MovieActorConverter{
    public MovieActor convertDtoToModel(MovieActorDto dto) {
        var model = new MovieActor();
        model.setMovieTitle(dto.getMovieTitle());
        model.setActors(dto.getActors());

        return model;
    }

    public MovieActorDto convertModelToDto(MovieActor movieActor) {
        MovieActorDto dto = MovieActorDto.builder()
                .movieTitle(movieActor.getMovieTitle())
                .actors(movieActor.getActors())
                .build();
        return dto;
    }

    public Set<MovieActorDto> convertModelsToDtos(Collection<MovieActor> models) {
        return models.stream()
                .map(this::convertModelToDto)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        //.collect(Collectors.toSet());
    }
}
