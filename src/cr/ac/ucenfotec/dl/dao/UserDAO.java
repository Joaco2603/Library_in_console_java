package cr.ac.ucenfotec.dl.dao;

import cr.ac.ucenfotec.bl.entities.User;
import java.util.ArrayList;

/**
 * Interfaz DAO para operaciones de persistencia de usuarios.
 * Define operaciones básicas para agregar, listar y sincronizar con almacenamiento.
 */
public interface UserDAO {
    /** Agrega un usuario y lo persiste. */
    void addUser(User u);

    /** Retorna la lista en memoria de usuarios. */
    ArrayList<User> getUsers();

    /** Carga los usuarios desde el almacenamiento (si aplica). */
    void load();

    /** Persiste los usuarios al almacenamiento. */
    void save();
}
