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
        /* Implementing the comparator to reverse que so there can be an up and a down queue. */
        Comparator<Passenger> stopUpComparator = Comparator.comparingInt(Passenger::getEndFloor);
        Comparator<Passenger> stopDownComparator = Comparator.comparingInt(Passenger::getEndFloor).reversed();

        /* Constructing all the queues using the comparators. */
        stopUp = new PriorityQueue<Passenger>(stopUpComparator);
        stopDown = new PriorityQueue<Passenger>(stopDownComparator);

        floorQueUp = new PriorityQueue<Integer>();
        floorQueDown = new PriorityQueue<Integer>(Collections.reverseOrder());

    }


    public boolean isFull() {
        /* Checks to see if the elevator is full. Used in addToElevator() */
        if ((stopUp.size() + stopDown.size()) < elevators3.elevatorCapacity) {
            return false;
        } else {
            return true;
        }

    }
/*  Another function used for testing. Prints all the queues going up and down.
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
 */
    /*laodAndunload takse all the passengers off the elevator that have arrived at their floor,
    * and loads all the passengers that need to go in the same direction as the elevator onto the
    * elevator.*/
    public void loadAndUnload() {
        unLoad();
        load();
    }
    /* Unload function to take off passengers. */
    private void unLoad() {
        /* Checks to see if there are passengers in going up queue.*/
        //NOTE this if statement looks redundate so i took it out last minute but on the off chance its not,
        //I'm just gonna leave it commented out.
       // if (!stopUp.isEmpty()) {
            /* Ensures stopUp is still not empty, check top of stack (top passenger) */
            while (!stopUp.isEmpty() && stopUp.peek().EndFloor == ElevFloor) {
                /*If they're at the floor, take them off. Once they're taken off, mark and track their
                * end time. */
                Passenger pass = stopUp.poll();
                pass.setEndTime(ElevatorSimulator.ticks);
                ElevatorSimulator.incrementAllPassengers();
                ElevatorSimulator.incrementAllTimes(pass.getTotalTime());
                ElevatorSimulator.calcMinAndMax(pass);
                /* Break out early if its empty (handles NullPointerException) */
                if (stopUp.isEmpty()) {
                    break;
                }
            }

       // }
        //Works the same as above.
        //if (!stopDown.isEmpty()) {
            while (!stopDown.isEmpty() && stopDown.peek().EndFloor == ElevFloor) {
                Passenger pass = stopDown.poll();
                pass.setEndTime(ElevatorSimulator.ticks);
                ElevatorSimulator.incrementAllPassengers();
                ElevatorSimulator.incrementAllTimes(pass.getTotalTime());
                ElevatorSimulator.calcMinAndMax(pass);
                if (stopDown.isEmpty()) {
                    break;
                }
            }
       // }
        /*Unqueues the current floor from the floorRequestQueue*/
        if (!floorQueUp.isEmpty()) {
            /*Can be multiple requests (multiple people can hit the same up/down button. */
            while (floorQueUp.peek() == ElevFloor) {
                floorQueUp.poll();
                if (floorQueUp.isEmpty()) {
                    break;
                }
            }
        }
        /*Unqueues the current floor from the floorRequestQueue, same as above. */
        if (!floorQueDown.isEmpty()) {
            while (floorQueDown.peek() == ElevFloor) {
                floorQueDown.poll();
                if (floorQueDown.isEmpty()) {
                    break;
                }
            }
        }
    }

    /* Load function takes passengers off their current floor and loads them into the elevator so long
    * as the elevator is not already full. (Note here that I implemented isFull after I wrote this but I'm scared to
    * change it/ran out of time. */
    public void load() {
        //Get the current floor passengers/actual floor instance.
        Floor floor = ElevatorSimulator.floorList.get(ElevFloor);
        //If elevator is going up, get the passengers going up on that floor.
        if (up == true) {
            /*Increment through the passengers going up and see if they have the same floor. PassengersToTake is the correct
            number of passengers getting taken off the floor. */
            int passengersToTake = floor.waitingUp.size();
            if (floor.waitingUp.size() > elevators3.elevatorCapacity - (stopUp.size() + stopDown.size())) {
                passengersToTake = elevators3.elevatorCapacity - (stopUp.size() + stopDown.size());
            }
            //Keep track of where in the stack we leave off, so we can re-request for the remaining passengers
            int leftOff = 0;
            /* Actually run through and remove passengers from the floor, re-add them onto the upQue on the elevator.
            * Request stop for their end floor (them pressing their desired floor) */
            for (int i = 0; i < passengersToTake; i++) {
                Passenger pass = floor.waitingUp.poll();
                stopUp.add(pass);
                floorQueUp.add(pass.EndFloor);
                leftOff = i;
            }
            /* Passengers stuck on the floor that may have been dequeued from unload, will need to requeue here. Runs
            * through remaining passengers that didn't make it on and requeues them. */
            for (int j = leftOff; j < floor.waitingUp.size(); j++) {
                Passenger pass = floor.waitingUp.poll();
                ElevatorSimulator.passengersToRequestAgain.add(pass);
            }
        } else {
            /* Works the same way but for down instead. */
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
        /* travel function moves the elevators up or down depending on direction and next stop. Can only move 5 floors at
        * a time.  */
        /*For up elevators...*/
        if (up == true) {
            /* Set elevator floor up 5 */
            ElevFloor += 5;
            /* See if the upque is empty, use this to check for next closest floor in same direction. */
            if (!floorQueUp.isEmpty()) {
                /*If there is one and its less than the ElevFLoor, then its within 5 of the currentFloor. Set current
                * floor to here. Otherwise, it will still just be incrimented by 5 */
                if (floorQueUp.peek() < ElevFloor) {
                    ElevFloor = floorQueUp.peek();
                }
            }
            /*Ensure that the elevator floor is not passed the ceiling, if it is bring it back down and switch directions.*/
            if (ElevFloor > elevators3.floorNumber - 1) {
                ElevFloor = elevators3.floorNumber - 1;
                up = false;
            }
            /*Same concept but going down.*/
        } else {
            ElevFloor -= 5;
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
    }

}