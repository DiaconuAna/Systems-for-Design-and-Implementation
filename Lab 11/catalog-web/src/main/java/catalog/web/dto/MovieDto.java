package catalog.web.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MovieDto extends BaseDto{
    private String title;
    private String director;
    private Long genreId;
    private String productionCountry;
}
