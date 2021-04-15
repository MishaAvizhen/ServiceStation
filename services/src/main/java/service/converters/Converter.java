package service.converters;

public interface Converter<E, D> {
    E convertToEntity(D dto);

    E convertToExistingEntity(D dto, E entity);

    D convertToDto(E entity);
}
