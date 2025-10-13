package cr.ac.ucenfotec.dl;

import cr.ac.ucenfotec.bl.entities.Reserve;
import java.util.ArrayList;

public class ReservesData {
    private ArrayList<Reserve> reserves;

    public ReservesData() {
        reserves = new ArrayList<>();
    }

    public void addReserve(Reserve r) {
        if (reserves == null) {
            reserves = new ArrayList<>();
        }
        reserves.add(r);
    }

    public ArrayList<Reserve> getReserves() {
        return reserves;
    }
}
