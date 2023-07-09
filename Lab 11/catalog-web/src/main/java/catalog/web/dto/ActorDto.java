package catalog.web.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class ActorDto extends BaseDto{
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private int popularity;
}
