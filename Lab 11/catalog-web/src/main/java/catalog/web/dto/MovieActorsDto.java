package catalog.web.dto;

import catalog.core.model.MovieActor;
import catalog.web.converter.BaseConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MovieActorsDto {
    private Set<MovieActorDto> movieActors;
}
