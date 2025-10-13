package cr.ac.ucenfotec.dl;

import java.util.ArrayList;

import cr.ac.ucenfotec.bl.entities.Role;

public class RoleData {
    private ArrayList<Role> roles;

    public RoleData() {
        roles = new ArrayList<>();

        roles.add(new Role(1, "admin", "Administrador del sistema"));
        roles.add(new Role(2, "user", "Usuario estándar"));
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }
}
