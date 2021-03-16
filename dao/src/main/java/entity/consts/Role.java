package entity.consts;

public enum Role {
    USER_ROLE("USER_ROLE"),
    ADMIN_ROLE("ADMIN_ROLE"),
    MASTER_ROLE("MASTER_ROLE"),
    GUEST_ROLE("GUEST_ROLE");

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
