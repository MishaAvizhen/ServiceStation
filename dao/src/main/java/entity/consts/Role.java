package entity.consts;

public enum Role {
    USER,
    ADMIN,
    MASTER,
    GUEST;

    public static Role defineRoleByRoleName(String inputRoleName) {
        for (Role role : values()) {
            if (inputRoleName.equals(role.name())) {
                return role;
            }
        }
        return null;
    }
}
