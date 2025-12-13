
package cr.ac.ucenfotec.bl.handlers;
import cr.ac.ucenfotec.dl.dao.UserDAO;
import cr.ac.ucenfotec.dl.jdbc.UsersDAOJdbc;
import cr.ac.ucenfotec.bl.entities.Role;
import cr.ac.ucenfotec.bl.entities.User;
import java.util.ArrayList;
import java.util.UUID;

public class UserHandler {
	private UserDAO dataUsers;

	public UserHandler() {
		dataUsers = new UsersDAOJdbc();
	}

    /**
     * Handler para operaciones relacionadas con usuarios. Actúa como capa
     * intermedia entre la UI y el DAO (`UsersData`).
     */

	public User addUser(String first_name, String last_name, String email, String password, int roleId) {
		RoleHandler roleHandler = new RoleHandler();
		Role role = roleHandler.getRoleByName(roleId == 1 ? "admin" : "user");
		User user = new User(UUID.randomUUID().toString(), first_name, last_name, email, password, role);
		dataUsers.addUser(user);
		return user;
	}

	public User findUserByEmailAndPassword(String email, String password) {
		for (User user : dataUsers.getUsers()) {
			if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}

	public ArrayList<User> getAllUsers() {
		return dataUsers.getUsers();
	}

	/** Persiste los usuarios al almacenamiento. */
	public void save() {
		dataUsers.save();
	}

	/**
	 * Indica si el usuario tiene rol 'admin'.
	 */
	public boolean isAdmin(User user) {
		if (user == null) return false;
		if (user.getRole() == null) return false;
		return "admin".equalsIgnoreCase(user.getRole().getRoleName());
	}
}
