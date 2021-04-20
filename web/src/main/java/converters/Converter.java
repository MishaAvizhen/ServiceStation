package converters;
public interface Converter<S, T> {
    T convertToServiceDto(S webDto);
    S convertToWebDto(T serviceDto);
}
