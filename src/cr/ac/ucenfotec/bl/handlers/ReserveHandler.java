
package cr.ac.ucenfotec.bl.handlers;
import cr.ac.ucenfotec.dl.ReservesData;
import cr.ac.ucenfotec.bl.entities.ReserveEntity;
import cr.ac.ucenfotec.bl.entities.BookEntity;
import cr.ac.ucenfotec.bl.entities.UserEntity;
import java.util.ArrayList;

public class ReserveHandler {
	private ReservesData dataReserves;

	public ReserveHandler() {
		dataReserves = new ReservesData();
	}

	public void addReserve(String reserveDate, String status, BookEntity book, UserEntity user) {
		ReserveEntity reserve = new ReserveEntity(reserveDate, status, book, user);
		dataReserves.addReserve(reserve);
	}

	public ArrayList<ReserveEntity> getAllReserves() {
		return dataReserves.getReserves();
	}
}
