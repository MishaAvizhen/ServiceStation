package entity.consts;

public enum Role {
    USER_ROLE("USER"),
    ADMIN_ROLE("ADMIN"),
    MASTER_ROLE("MASTER"),
    GUEST_ROLE("GUEST");

    private String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public static Role defineRoleByRoleName(String inputRoleName) {
        for (Role role : values()) {
            if (inputRoleName.equals(role.roleName)) {
                return role;
            }
        }
        return null;
    }
}
