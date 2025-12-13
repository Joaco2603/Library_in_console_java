package cr.ac.ucenfotec.bl.entities;

import java.util.Objects;

/**
 * Representa un usuario del sistema (hereda de {@link Person}).
 * Contiene credenciales y el rol.
 */
public class User extends Person {
    private String password;
    private Role role;

    public User(String id, String first_name, String last_name, String email, String password, Role role) {
        super(id, first_name, last_name, email);
        this.password = password;
        this.role = role;
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

    @Override
    public String toString() {
        return "ID: " + getId() + ", Name: " + getFullName() + ", Email: " + getEmail() + ", Role: " + role.getRoleName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    // toCSV removed per request - persistence handled in DAO implementations
}
