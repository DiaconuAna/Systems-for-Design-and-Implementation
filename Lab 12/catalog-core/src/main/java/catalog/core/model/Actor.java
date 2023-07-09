package catalog.core.model;

import lombok.*;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Actor extends BaseEntity<Long>{
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private int popularity;
}