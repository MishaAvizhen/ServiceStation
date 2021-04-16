package dto;

import entity.consts.Role;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "User entity for web")

public class UserWebDto {

    private String username;
    private String password;
    private String phoneNumber;
    private String email;
    private Role role;


}
