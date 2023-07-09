package ro.ubb.socket.domain.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
