package catalog.core.model;


import lombok.*;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class MovieActor {
    private String movieTitle;
    private List<String> actors;

    public MovieActor(){
        movieTitle = "";
        actors = new ArrayList<>();
    }
}
