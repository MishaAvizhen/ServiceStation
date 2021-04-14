package dto;

import entity.consts.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserWebDto {
    private Long userId;
    private String username;
    private String password;
    private String phoneNumber;
    private String email;
    private Role role;


}
