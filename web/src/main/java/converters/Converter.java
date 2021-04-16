package converters;
// TODO переименовать в Converter.convert convertToWeb convertToServiceDto
public interface Converter<S, T> {
    T convertToServiceDto(S webDto);
    S convertToWebDto(T serviceDto);
}
