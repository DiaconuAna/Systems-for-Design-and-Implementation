package catalog.core.model;


import lombok.*;

import javax.persistence.Entity;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Movie extends BaseEntity<Long>{
    private String title;
    private String director;
    private Long genreId;
    private String productionCountry;
}
