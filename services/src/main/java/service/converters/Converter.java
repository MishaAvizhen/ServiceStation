package service.converters;

import entity.User;
import service.dto.UserRegistrationDto;

public interface Converter<E, D> {
    E convertToEntity(D dto);

    E convertToExistingEntity(D dto, E entity);

    D convertToDto(E entity);
}
