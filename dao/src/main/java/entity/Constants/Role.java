package entity.constants;

public enum Role {
    USER_ROLE(1L),
    ADMIN_ROLE(2L),
    GUEST_ROLE(4L),
    MASTER_ROLE(3L);

    private Long roleId;

    Role(Long roleId) {
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public static Role defineRoleByRoleId(Long inputRoleId) {
        for (Role role : values()) {
            if (inputRoleId.equals(role.roleId)) {
                return role;
            }
        }
        return null;
    }
}
