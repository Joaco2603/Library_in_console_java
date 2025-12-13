package cr.ac.ucenfotec.dl.dao;

import cr.ac.ucenfotec.bl.entities.Reserve;
import java.util.ArrayList;

/**
 * Interfaz DAO para persistencia de reservas.
 */
public interface ReserveDAO {
    /** Agrega una reserva y persiste el cambio. */
    void addReserve(Reserve r);

    /** Retorna la lista de reservas en memoria. */
    ArrayList<Reserve> getReserves();

    /** Carga reservas desde almacenamiento. */
    void load();

    /** Persiste reservas al almacenamiento. */
    void save();
}
