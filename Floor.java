import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Floor {
    Queue<Passenger> waitingUp;
    Queue<Passenger> waitingDown;

    public Floor() {
        waitingUp = new LinkedList<Passenger>();
        waitingDown = new LinkedList<Passenger>();
    }
    public void addPass(Passenger pass) {
        if (pass.StartFloor < pass.EndFloor) {
            this.waitingUp.add(pass);
        } else {
            this.waitingDown.add(pass);
        }
    }

}