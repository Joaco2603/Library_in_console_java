package cr.ac.ucenfotec.bl.entities;

public class Role {
    private int id;
    private String roleName;
    private String description;

    public Role(int id, String roleName, String description) {
        this.id = id;
        this.roleName = roleName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int setId(int id) {
        return this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return "ID: " + id + ", Role Name: " + roleName + ", Description: " + description;
    }
}
