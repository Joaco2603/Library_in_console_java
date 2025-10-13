package cr.ac.ucenfotec.dl;

import cr.ac.ucenfotec.bl.entities.User;
import cr.ac.ucenfotec.bl.handlers.RoleHandler;

import java.util.ArrayList;
import java.util.UUID;

public class UsersData {
    private ArrayList<User> users;

    // public UsersData() {
    // users = new ArrayList<>();

    // RoleHandler roleHandler = new RoleHandler();

    // users.add(new UserEntity(UUID.randomUUID().toString(), "john_doe",
    // "john@example.com", "1234", roleHandler.getRoleByName("Admin")));
    // }
    public UsersData() {
        users = new ArrayList<>();

        RoleHandler roleHandler = new RoleHandler();

        // Verify the role exists before creating the user
        var adminRole = roleHandler.getRoleByName("admin");
        if (adminRole != null) {
            users.add(new User(UUID.randomUUID().toString(), "john", "doe", "john@example.com", "1234", adminRole));
        } else {
            System.err.println("Warning: Admin role not found. User not created.");
        }
    }

    public void addUser(User u) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(u);
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
