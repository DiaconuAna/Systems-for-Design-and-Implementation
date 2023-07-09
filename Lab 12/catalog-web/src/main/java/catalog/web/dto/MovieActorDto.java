package catalog.web.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class MovieActorDto extends BaseDto{
    // private MovieDto movieDto;
    // private ActorsDto actorsDto;
    // movie title, list of actor names
    private String movieTitle;
    private List<String> actors;
}
