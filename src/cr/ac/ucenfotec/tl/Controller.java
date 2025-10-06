package cr.ac.ucenfotec.tl;

import cr.ac.ucenfotec.ui.UI;
import cr.ac.ucenfotec.bl.handlers.RoleHandler;
import cr.ac.ucenfotec.bl.handlers.UserHandler;
import cr.ac.ucenfotec.bl.entities.UserEntity;

import java.io.IOException;

public class Controller {
    // Instancia de la clase UI para manejar la interfaz de usuario
    private final UI UIInterface = new UI();
    // Handler para manejar usuarios
    private final UserHandler userHandler = new UserHandler();
    // Simula si el usuario esta logeado o no
    private boolean isLogged = false;
    // Usuario actualmente logeado
    private UserEntity currentUser = null;

    public void start() throws IOException {
        int option = -1;

        do {
            UIInterface.displayAccess();
            option = UIInterface.readOption();
            processAccess(option);
        } while (!isLogged);

        do {
            UIInterface.displayMenu();
            option = UIInterface.readOption();
            processOption(option);
        } while (option != 5);
    }

    public void processAccess(int option) throws IOException {
        switch (option) {
            case 1:
                processLogin();
                break;
            case 2:
                processRegister();
                break;
            default:
                UIInterface.displayError();
                break;
        }
    }

    public void processLogin() {
        // Solicitar credenciales al usuario
        String[] credentials = UIInterface.getLoginCredentials();
        String email = credentials[0];
        String password = credentials[1];

        // Buscar usuario en el sistema
        UserEntity user = userHandler.findUserByEmailAndPassword(email, password);

        if (user != null) {
            currentUser = user;
            isLogged = true;
            UIInterface.displayLoginSuccess(user.getUsername());
        } else {
            UIInterface.displayLoginFailure();
            isLogged = false;
        }
    }

    public void processRegister() {
        // Solicitar datos de registro al usuario
        String[] registerData = UIInterface.getRegisterData();
        String username = registerData[0];
        String password = registerData[1];
        String email = registerData[2];

        RoleHandler roleHandler = new RoleHandler();

        // Agregar el usuario al sistema
        userHandler.addUser(username, email, password, roleHandler.getDefaultRoleId());

        UIInterface.displayRegisterSuccess();
        isLogged = true;
    }

    public void processLogout() {
        isLogged = false;
    }

    public void processOption(int option) throws IOException {
        do {
            UIInterface.displayMenu();
            option = UIInterface.readOption();
            switch (option) {
                case 1:
                    processBookReservation(option);
                    break;
                case 2:
                    processBookReturn(option);
                    break;
                case 3:
                    processBookSearch(option);
                    // lógica para buscar libro
                    break;
                case 4:
                    displayUsers();
                    break;
                case 5:
                    UIInterface.displayExit();
                    break;
                default:
                    UIInterface.displayError();
                    break;
            }
        } while (option != 5);
    }

    public void processBookReservation(int option) {
        // Lógica para reservar un libro
        UIInterface.displayBookReservation(option);
    }

    public void processBookReturn(int option) {
        // Lógica para devolver un libro
        UIInterface.displayBookReturn(option);
    }

    public void processBookSearch(int option) {
        // Lógica para buscar un libro
        UIInterface.displayBookSearch(option);
    }

    public void displayUsers() {
        userHandler.getAllUsers().forEach(user -> UIInterface.displayUserInfo(user));
    }

}
