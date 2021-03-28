package converters;

public interface ConverterFromFirstDtoToSecond<UD, WD> {
    UD convertToEntityRegistrationDto(WD webDto);
}
