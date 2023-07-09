package catalog.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractDto extends BaseDto{
    private Long ActorId;
    private Long MovieId;
}
