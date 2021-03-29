package converters;

public interface ConverterFromFirstDtoToSecond<S,T> {
    T convertFromSourceDtoToTargetDto(S sourceDto);
}
