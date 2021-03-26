package converters;

public interface ConverterFromFirstDtoToSecond<UD, WD> {
    UD convertToUserRegistrationDto(WD webDto);
}
