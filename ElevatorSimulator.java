import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

public class ElevatorSimulator extends elevators3 {
    static int ticks;

    static ArrayList<Floor> floorList;

    static ArrayList<Elevator> elevatorList;

    static ArrayList<Passenger> passengersToRequestAgain;

    static int numberOfPassengers;

    static int allTimes;
    static int max;
    static int min;



    public ElevatorSimulator() {
        ticks = 0;

        this.elevatorList = new ArrayList<Elevator>();

        this.passengersToRequestAgain = new ArrayList<Passenger>();

        for (int i = 0; i < elevatorNumber; i++) {
            Elevator newElevator = new Elevator();
            this.elevatorList.add(newElevator);
        }

        this.floorList = new ArrayList<Floor>();

        for (int i = 0; i < floorNumber; i++) {
            Floor newFloor = new Floor();
            this.floorList.add(newFloor);
        }
        allTimes = 0;
        min = duration;
        max = 0;
        numberOfPassengers = 0;
    }

    public static void calcMinAndMax(Passenger pass) {
        int totalTime = pass.getTotalTime();
        if (totalTime < min) {
            min = totalTime;
        }
        if (totalTime > max) {
            max = totalTime;
        }
    }

    public void runSimulation() {
        while (ticks < duration) {
            ticks++;
            generatePassengers();
            for (int i = 0; i < elevatorNumber; i++) {
                elevatorList.get(i).travel();
                elevatorList.get(i).loadAndUnload();
            }
        }
        getFinalReport();
    }

    private void getFinalReport() {
        double averageTime = allTimes / numberOfPassengers;
        System.out.println("Average time: " + averageTime);
        System.out.println("Max time: " + max);
        System.out.println("Min time: " + min);
    }

    public void generatePassengers() {
        boolean wasAdded = false;
        //Iterate through each floor
        for (int i = 0; i < floorNumber; i++) {
            //Check prob for whether a passenger will be generated or not
            double passengerCheck = Math.random();
            //If correct prob, add new passenger to current floor.
            if (passengerCheck <= passengers) {
                Floor toAddTo = floorList.get(i);
                //Passenger constructor
                Passenger pass = new Passenger(i, ticks);
                numberOfPassengers += 1;
                toAddTo.addPass(pass);
                wasAdded = addToElevator(pass);
                if (wasAdded == false) {
                    passengersToRequestAgain.add(pass);
                }
            }
        }
        printWaitingPassengers();
    }

    public boolean addToElevator(Passenger pass) {
        boolean passAdded = false;
        for (int i = 0; i < elevatorList.size(); i++) {
            Elevator elev = elevatorList.get(i);
            if (elev.isFull() == false) {
                if (pass.up == true) {
                    elev.floorQueUp.add(pass.StartFloor);
                    passAdded = true;
                } else {
                    elev.floorQueDown.add(pass.StartFloor);
                    passAdded = true;
                }
            }
        }
        return passAdded;
    }

    /*

    public boolean addToElevator(Passenger pass) {
        boolean passAdded = false;
        for (int i = 0; i < elevatorList.size(); i++) {
            Elevator elev = elevatorList.get(i);
            if (elev.isFull() == false) {
                /* Trying w/out checking if higher or lower than current floor
                if (elev.up == true) {
                    if (pass.StartFloor >= elev.ElevFloor && pass.up == true) {
                        elev.floorQueUp.add(pass.StartFloor);
                        passAdded = true;
                    }
                    if (pass.up == false) {
                        elev.floorQueDown.add(pass.StartFloor);
                        passAdded = true;
                    }
                } else {
                    if (pass.up == false && pass.StartFloor <= elev.ElevFloor) {
                        elev.floorQueDown.add(pass.StartFloor);
                        passAdded = true;
                    } else if (pass.up == true) {
                        elev.floorQueUp.add(pass.StartFloor);
                        passAdded = true;
                    }
                }
            }
            elev.printQues();
            printNeedtoReq();
        }
        return passAdded;
    }
    */

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

    public static void printNeedtoReq() {
        System.out.println("request again list \n");
        ArrayList<Passenger> temp2 = new ArrayList<>(passengersToRequestAgain);
        while (temp2.isEmpty() == false && temp2.get(0) != null) {
            temp2.remove(0).printPassenger();
        }
        System.out.println("End request again list.");
    }

}
