package service.dto;


import entity.consts.Role;

public class UserRegistrationDto {
    private String username;
    private String password;
    private String phoneNumber;
    private String email;
    private Role role;

    private UserRegistrationDto(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.phoneNumber = builder.phoneNumber;
        this.email = builder.email;
        this.role = builder.role;
    }

    public UserRegistrationDto() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public static class Builder {
        private String username;
        private String password;
        private String phoneNumber;
        private String email;
        private Role role;

        public Builder() {
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setRole(Role role) {
            this.role = role;
            return this;
        }

        public UserRegistrationDto build() {
            return new UserRegistrationDto(this);

        }
    }

    @Override
    public String toString() {
        return "UserRegistrationDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
