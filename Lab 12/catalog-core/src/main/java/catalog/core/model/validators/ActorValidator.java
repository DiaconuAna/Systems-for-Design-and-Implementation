package catalog.core.model.validators;


import catalog.core.model.Actor;
import org.springframework.stereotype.Component;

@Component
public class ActorValidator implements Validator<Actor>{
    @Override
    public void validate(Actor entity) throws ValidatorException {
        if (entity.getFirstName().equals("") || entity.getLastName().equals("")){
            throw new ValidatorException("The name of the actor is an empty string.");
        }
        if (entity.getAge() < 0 || entity.getAge() > 90){
            throw new ValidatorException("Invalid age for the actor.");
        }
        if (!(entity.getGender().equals("female") || entity.getGender().equals("male") || entity.getGender().equals("non-binary"))){
            throw new ValidatorException("Invalid gender. Pick Female, Male or Non-Binary");
        }
        if(entity.getPopularity()<0 || entity.getPopularity()>10){
            throw new ValidatorException("Actor popularity should be between 0 and 10");
        }

    }
}
