package converters;

public interface Converter<En, D> {
    D convertToDto(En entity);
    En convertToEntity(D dto);




}
