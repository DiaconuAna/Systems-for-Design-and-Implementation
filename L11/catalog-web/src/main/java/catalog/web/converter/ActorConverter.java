package catalog.web.converter;

import catalog.web.dto.ActorDto;
import catalog.core.model.Actor;
import org.springframework.stereotype.Component;

@Component
public class ActorConverter extends BaseConverter<Actor, ActorDto> {

    @Override
    public Actor convertDtoToModel(ActorDto dto) {
        var model = new Actor();
        model.setId(dto.getId());
        model.setFirstName(dto.getFirstName());
        model.setLastName(dto.getLastName());
        model.setAge(dto.getAge());
        model.setPopularity(dto.getPopularity());
        model.setGender(dto.getGender());
        return model;
    }

    @Override
    public ActorDto convertModelToDto(Actor actor) {
        //System.out.println("ACTOR IS: " + actor);
        //ActorDto dto = new ActorDto(actor.getFirstName(), actor.getLastName(), actor.getAge(), actor.getGender(), actor.getPopularity());
        ActorDto dto = ActorDto.builder()
                .age(actor.getAge())
                .firstName(actor.getFirstName())
                .lastName(actor.getLastName())
                .gender(actor.getGender())
                .popularity(actor.getPopularity())
                .build();
        dto.setId(actor.getId());
        return dto;
    }
}
