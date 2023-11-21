import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Collections;

public class Elevator {
    /* Elevator globals: up gives direction elevator is headed. ElevFloor is the current floor.*/
    boolean up;
    int ElevFloor;
    /* stopUp and stopDown are the passengers that are actually ON the elevator. In the form of priority que. */
    PriorityQueue<Passenger> stopUp;
    PriorityQueue<Passenger> stopDown;
    /* floorQueUp and floorQueDown are priority queues of integers that have the endStop of passengers already on board,
    * and the firstFloor of passengers waiting to be picked up. Will be up or down depending on direction of the
    * passenger. */
    PriorityQueue<Integer> floorQueUp;
    PriorityQueue<Integer> floorQueDown;

    /* Elevator class constructor. */
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


    public boolean isFull() {
        int check = stopUp.size() + stopDown.size();
        System.out.println("SIZE OF ELEVATOR " + check);
        if ((stopUp.size() + stopDown.size()) < elevators3.elevatorCapacity) {
            return false;
        } else {
            return true;
        }

    }

    public void printQues() {
        System.out.println("Up Que \n");
        PriorityQueue<Integer> temp = new PriorityQueue<>(floorQueUp);
        while (temp.isEmpty() == false && temp.peek() != null) {
            System.out.print(temp.poll() + " ");
        }
        System.out.println("End of up que.");

        System.out.println("Down Que \n");
        PriorityQueue<Integer> temp2 = new PriorityQueue<>(floorQueDown);
        while (temp2.isEmpty() == false && temp2.peek() != null) {
            System.out.print(temp2.poll() + " ");
        }
        System.out.println("End down que.");


    }
    public void loadAndUnload() {
        unLoad();
        load();
    }

    private void unLoad() {
        if (!stopUp.isEmpty()) {
            while (!stopUp.isEmpty() && stopUp.peek().EndFloor == ElevFloor) {
                Passenger pass = stopUp.poll();
                pass.endTime = ElevatorSimulator.ticks;
                ElevatorSimulator.allTimes += pass.getTotalTime();
                ElevatorSimulator.calcMinAndMax(pass);
                if (stopUp.isEmpty()) {
                    break;
                }
            }

        }
        if (!stopDown.isEmpty()) {
            while (stopDown.peek().EndFloor == ElevFloor) {
                Passenger pass = stopDown.poll();
                pass.endTime = ElevatorSimulator.ticks;
                ElevatorSimulator.allTimes += pass.getTotalTime();
                ElevatorSimulator.calcMinAndMax(pass);
                if (stopDown.isEmpty()) {
                    break;
                }
            }
        }
        if (!floorQueUp.isEmpty()) {
            while (floorQueUp.peek() == ElevFloor) {
                floorQueUp.poll();
                if (floorQueUp.isEmpty()) {
                    break;
                }
            }
        }
        if (!floorQueDown.isEmpty()) {
            while (floorQueDown.peek() == ElevFloor) {
                floorQueDown.poll();
                if (floorQueDown.isEmpty()) {
                    break;
                }
            }
        }
    }

    public void load() {
        //See load up, but this if for down
        Floor floor = ElevatorSimulator.floorList.get(ElevFloor);

        if (up == true) {
            int passengersToTake = floor.waitingUp.size();
            if (floor.waitingUp.size() > elevators3.elevatorCapacity - (stopUp.size() + stopDown.size())) {
                passengersToTake = elevators3.elevatorCapacity - (stopUp.size() + stopDown.size());
            }
            int leftOff = 0;
            for (int i = 0; i < passengersToTake; i++) {
                Passenger pass = floor.waitingUp.poll();
                stopUp.add(pass);
                floorQueUp.add(pass.EndFloor);
                leftOff = i;
            }
            for (int j = leftOff; j < floor.waitingUp.size(); j++) {
                Passenger pass = floor.waitingUp.poll();
                ElevatorSimulator.passengersToRequestAgain.add(pass);
            }
        } else {
            int passengersToTake = floor.waitingDown.size();
            if (floor.waitingDown.size() > (elevators3.elevatorCapacity - (stopUp.size() + stopDown.size()))) {
                passengersToTake = elevators3.elevatorCapacity - (stopUp.size() + stopDown.size());
            }
            int leftOff = 0;
            for (int i = 0; i < passengersToTake; i++) {
                Passenger pass = floor.waitingDown.poll();
                if (pass != null) {
                    stopDown.add(pass);
                }
                floorQueDown.add(pass.EndFloor);
                leftOff = i;
            }
            for (int j = leftOff; j < floor.waitingDown.size(); j++) {
                Passenger pass = floor.waitingDown.poll();
                ElevatorSimulator.passengersToRequestAgain.add(pass);
            }
        }
    }

    public void travel() {
        if (up == true) {
            ElevFloor += 5;
            printQues();
            if (!floorQueUp.isEmpty()) {
                if (floorQueUp.peek() < ElevFloor) {
                    ElevFloor = floorQueUp.peek();
                }
            }
            if (ElevFloor > elevators3.floorNumber - 1) {
                ElevFloor = elevators3.floorNumber - 1;
                up = false;
            }
        } else {
            ElevFloor -= 5;
            printQues();
            if (!floorQueDown.isEmpty()) {
                if (floorQueDown.peek() > ElevFloor) {
                    ElevFloor = floorQueDown.peek();
                }
            }
            if (ElevFloor < 0) {
                ElevFloor = 0;
                up = true;
            }
        }
        System.out.println("CURRENT FLOOR" + ElevFloor);
    }

}