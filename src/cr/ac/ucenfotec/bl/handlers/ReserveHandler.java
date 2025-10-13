
package cr.ac.ucenfotec.bl.handlers;
import cr.ac.ucenfotec.dl.ReservesData;
import cr.ac.ucenfotec.bl.entities.Reserve;
import cr.ac.ucenfotec.bl.entities.Book;
import cr.ac.ucenfotec.bl.entities.User;
import java.util.ArrayList;

public class ReserveHandler {
	private ReservesData dataReserves;

	public ReserveHandler() {
		dataReserves = new ReservesData();
	}

	public Reserve addReserve(String reserveDate, String status, Book book, User user) {
		Reserve reserve = new Reserve(reserveDate, status, book, user);
		dataReserves.addReserve(reserve);
		return reserve;
	}

	public ArrayList<Reserve> getAllReserves() {
		return dataReserves.getReserves();
	}

	public Reserve findActiveReserveByBook(Book book) {
		if (dataReserves.getReserves() == null) {
			return null;
		}
		for (Reserve reserve : dataReserves.getReserves()) {
			if (reserve.getBook().getIsbn().equals(book.getIsbn()) && reserve.getStatus().equalsIgnoreCase("ACTIVE")) {
				return reserve;
			}
		}
		return null;
	}

	public Reserve findActiveReserveByBookAndUser(Book book, User user) {
		if (dataReserves.getReserves() == null) {
			return null;
		}
		for (Reserve reserve : dataReserves.getReserves()) {
			boolean sameBook = reserve.getBook().getIsbn().equals(book.getIsbn());
			boolean sameUser = reserve.getUser().getId().equals(user.getId());
			boolean isActive = reserve.getStatus().equalsIgnoreCase("ACTIVE");
			if (sameBook && sameUser && isActive) {
				return reserve;
			}
		}
		return null;
	}

	public ArrayList<Reserve> getActiveReservesByUser(User user) {
		ArrayList<Reserve> activeReserves = new ArrayList<>();
		if (dataReserves.getReserves() == null) {
			return activeReserves;
		}
		for (Reserve reserve : dataReserves.getReserves()) {
			boolean sameUser = reserve.getUser().getId().equals(user.getId());
			boolean isActive = reserve.getStatus().equalsIgnoreCase("ACTIVE");
			if (sameUser && isActive) {
				activeReserves.add(reserve);
			}
		}
		return activeReserves;
	}

	public void updateReserveStatus(Reserve reserve, String status) {
		if (reserve != null) {
			reserve.setStatus(status);
		}
	}
}
