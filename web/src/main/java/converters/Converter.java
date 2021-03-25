package converters;

public interface Converter<En, D> {
    D convertToDto(En entity);
}
