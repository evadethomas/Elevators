import java.util.ArrayList;
import java.util.Random;

public class ElevatorSimulator extends elevators3 {
    static int ticks;

    static ArrayList<Floor> floorList;

    static ArrayList<Elevator> elevatorList;



    public ElevatorSimulator() {
        ticks = 0;

        this.elevatorList = new ArrayList<Elevator>();

        for (int i = 0; i < elevatorNumber; i++) {
            Elevator newElevator = new Elevator();
            this.elevatorList.add(newElevator);
        }

        this.floorList = new ArrayList<Floor>();

        for (int i = 0; i < floorNumber; i++) {
            Floor newFloor = new Floor();
            this.floorList.add(newFloor);
        }
    }
    public void runSimulation() {
        while (ticks < duration) {
            ticks++;
            generatePassengers();
        }
    }

    public void generatePassengers() {
        //Iterate through each floor
        for (int i = 0; i < floorNumber; i++) {
            //Check prob for whether a passenger will be generated or not
            double passengerCheck = Math.random();
            //If correct prob, add new passenger to current floor.
            if (passengerCheck <= passengers) {
                Floor toAddTo = floorList.get(i);
                //Passenger constructor
                Passenger pass = new Passenger(i, ticks);
                toAddTo.addPass(pass);
            }
        }
        printWaitingPassengers();
    }

    public static void printWaitingPassengers() {
        //Implimented for testing
        //For each floor, iterate through and print out passengers currently on the floor waiting to be picked up.
        for (int i = 0; i < floorNumber; i++) {
            Floor currFloor = floorList.get(i);
            System.out.println();
            System.out.println("Passengers on floor " + i + " going up:\n");

            for (Passenger element : currFloor.waitingUp) {
                element.printPassenger();
            }

            System.out.println("Passengers on floor " + i + " going down:\n");

            for (Passenger element : currFloor.waitingDown) {
                element.printPassenger();
            }

        }
    }

}
