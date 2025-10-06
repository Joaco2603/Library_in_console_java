package cr.ac.ucenfotec.dl;

import java.util.ArrayList;

import cr.ac.ucenfotec.bl.entities.RoleEntity;

public class RoleData {
    private ArrayList<RoleEntity> roles;

    public RoleData() {
        roles = new ArrayList<>();
    }

    public void addRole(RoleEntity role) {
        roles.add(role);
    }

    public ArrayList<RoleEntity> getRoles() {
        return roles;
    }
}
