package cr.ac.ucenfotec.bl.handlers;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucenfotec.bl.entities.RoleEntity;

public class RoleHandler {
    private List<RoleEntity> roles;

    public RoleHandler() {
        roles = new ArrayList<>();
    }

    public void addRole(RoleEntity role) {
        roles.add(role);
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public RoleEntity getRoleByName(String roleName) {
        for (RoleEntity role : roles) {
            if (role.getRoleName().equals(roleName)) {
                return role;
            }
        }
        return null;
    }

    public int getDefaultRoleId() {
        RoleEntity defaultRole = getRoleByName("User");
        return defaultRole != null ? defaultRole.getId() : -1; // Return -1 if no default role found
    }
}
