package cr.ac.ucenfotec.bl.handlers;

import cr.ac.ucenfotec.bl.entities.Role;
import java.util.ArrayList;
import java.util.UUID;

public class RoleHandler {
    private ArrayList<Role> roles;

    public RoleHandler() {
        roles = new ArrayList<>();
        // Initialize default roles
        roles.add(new Role(1, "admin", "Admin"));
        roles.add(new Role(2, "user", "User"));
        roles.add(new Role(3, "librarian", "Librarian"));
    }

    public Role getRoleByName(String roleName) {
        for (Role role : roles) {
            System.out.println(role);
            if (role.getRoleName().equals(roleName)) {
                return role;
            }
        }
        return null;
    }

    public int getDefaultRoleId(){
        return 2;
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }
}
