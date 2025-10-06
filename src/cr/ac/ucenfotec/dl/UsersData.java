package cr.ac.ucenfotec.dl;

import cr.ac.ucenfotec.bl.entities.UserEntity;
import cr.ac.ucenfotec.bl.handlers.RoleHandler;

import java.util.ArrayList;
import java.util.UUID;

public class UsersData {
    private ArrayList<UserEntity> users;

    public UsersData() {
        users = new ArrayList<>();

        RoleHandler roleHandler = new RoleHandler();

        users.add(new UserEntity(UUID.randomUUID().toString(), "john_doe", "john@example.com", "1234", roleHandler.getRoleByName("Admin")));
    }

    public void addUser(UserEntity u) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(u);
    }

    public ArrayList<UserEntity> getUsers() {
        return users;
    }
}
