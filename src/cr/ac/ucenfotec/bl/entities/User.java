package cr.ac.ucenfotec.bl.entities;

public class User {
    private String id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private Role role;

    public User(String id, String first_name, String last_name, String email, String password, Role role) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return first_name + " " + last_name;
    }

    public void setName(String name) {
        this.first_name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String toString() {
        return "ID: " + id + ", Name: " + getFullName() + ", Email: " + email + ", Role: " + role.getRoleName();
    }
}
