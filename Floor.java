import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Floor {
    /* Floor class implements a linked-list queue of passengers waiting to go up and down*/
    Queue<Passenger> waitingUp;
    Queue<Passenger> waitingDown;
    /* Floor constructor */
    public Floor() {

        if (elevators3.structures == "linked") {
            waitingUp = new LinkedList<Passenger>();
            waitingDown = new LinkedList<Passenger>();
        } else {
            waitingUp = new ArrayDeque<Passenger>();
            waitingDown = new ArrayDeque<Passenger>();
        }



    }
    /* Adds a passenger to the floor, adading to either the going up or going down queue. */
    public void addPass(Passenger pass) {
        if (pass.StartFloor < pass.EndFloor) {
            this.waitingUp.add(pass);
        } else {
            this.waitingDown.add(pass);
        }
    }

}