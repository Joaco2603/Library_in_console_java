package cr.ac.ucenfotec.dl;

import cr.ac.ucenfotec.bl.entities.ReserveEntity;
import java.util.ArrayList;

public class ReservesData {
    private ArrayList<ReserveEntity> reserves;

    public ReservesData() {
        reserves = new ArrayList<>();
    }

    public void addReserve(ReserveEntity r) {
        if (reserves == null) {
            reserves = new ArrayList<>();
        }
        reserves.add(r);
    }

    public ArrayList<ReserveEntity> getReserves() {
        return reserves;
    }
}
