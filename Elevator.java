import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Collections;

public class Elevator {
    boolean up;
    int ElevFloor;

    PriorityQueue<Passenger> stopUp;
    PriorityQueue<Passenger> stopDown;
    PriorityQueue<Integer> floorQueUp;
    PriorityQueue<Integer> floorQueDown;



    public Elevator() {
        this.ElevFloor = 0;
        up = true;
        Comparator<Passenger> stopUpComparator = Comparator.comparingInt(Passenger::getEndFloor);
        Comparator<Passenger> stopDownComparator = Comparator.comparingInt(Passenger::getEndFloor).reversed();
        stopUp = new PriorityQueue<Passenger>(stopUpComparator);
        stopDown = new PriorityQueue<Passenger>(stopDownComparator);

        floorQueUp = new PriorityQueue<Integer>();
        floorQueDown = new PriorityQueue<Integer>(Collections.reverseOrder());


    }


}