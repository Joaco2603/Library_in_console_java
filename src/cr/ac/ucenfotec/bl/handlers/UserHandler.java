
package cr.ac.ucenfotec.bl.handlers;
import cr.ac.ucenfotec.dl.UsersData;
import cr.ac.ucenfotec.bl.entities.RoleEntity;
import cr.ac.ucenfotec.bl.entities.UserEntity;
import java.util.ArrayList;
import java.util.UUID;

public class UserHandler {
	private UsersData dataUsers;

	public UserHandler() {
		dataUsers = new UsersData();
	}

	public void addUser(String username, String email, String password, int roleId) {
		RoleHandler roleHandler = new RoleHandler();
		RoleEntity role = roleHandler.getRoleByName(roleId == 1 ? "Admin" : "User");
		UserEntity user = new UserEntity(UUID.randomUUID().toString(), username, email, password, role);
		dataUsers.addUser(user);
	}

	public UserEntity findUserByEmailAndPassword(String email, String password) {
		for (UserEntity user : dataUsers.getUsers()) {
			if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}

	public ArrayList<UserEntity> getAllUsers() {
		return dataUsers.getUsers();
	}
}
