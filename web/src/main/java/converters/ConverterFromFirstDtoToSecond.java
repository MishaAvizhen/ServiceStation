package converters;
// TODO переименовать в Converter.convert convertToWeb convertToServiceDto
public interface ConverterFromFirstDtoToSecond<S, T> {
    T convertFromSourceDtoToTargetDto(S sourceDto);
}
