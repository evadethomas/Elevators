import java.util.ArrayList;
/*Elevator simulator class actually runs the simulation, calls instances of all other classes and extends the Elevators3
* class. */
public class ElevatorSimulator extends elevators3 {
    //Ticks are the current ticks
    static int ticks;
    //floorList contains all floors, each of which has a que of passengers.
    static ArrayList<Floor> floorList;
    //ElevatorList contains all the elevators, instances of the elevators class.
    static ArrayList<Elevator> elevatorList;
    /* PassengersToRequestAgain are passengers that needed to be re-qued because the elevator capacity was too high
     * (This is often not used).
     */
    static ArrayList<Passenger> passengersToRequestAgain;
    //Total number of passengers
    static int numberOfPassengers;
    //Total number of time (to calculate the average)
    static int allTimes;
    //Max time passenger is on elevator
    static int max;
    //min time passenger is on elevator
    static int min;


    //Constructor for ElevatorSimulator class
    public ElevatorSimulator() {
        //Initializing class global variables
        ticks = 0;

        this.elevatorList = new ArrayList<Elevator>();

        this.passengersToRequestAgain = new ArrayList<Passenger>();
        //Creates proper number of elevators depending on properties file variable.
        for (int i = 0; i < elevatorNumber; i++) {
            Elevator newElevator = new Elevator();
            this.elevatorList.add(newElevator);
        }

        this.floorList = new ArrayList<Floor>();
        //Creates proper number of elevators depending on properties file variable.
        for (int i = 0; i < floorNumber; i++) {
            Floor newFloor = new Floor();
            this.floorList.add(newFloor);
        }
        allTimes = 0;
        min = duration;
        max = 0;
        numberOfPassengers = 0;
    }

    /*Calculate the minAndMax (called once a passenger exits in unload() method in elevator class)
    * Used for the report function to get min and max times. */
    public static void calcMinAndMax(Passenger pass) {
        int totalTime = pass.getTotalTime();
        if (totalTime < min) {
            min = totalTime;
        }
        if (totalTime > max) {
            max = totalTime;
        }
    }

    /*Runs the actual simulation, incrimenting through ticks. First it generates passengers, travels, unloads, then
    * loads. It also handles when passengers need to re-request. */
    public void runSimulation() {
        //While loopo incriments ticks up to given duration from property file.
        while (ticks < duration) {
            ticks++;
            //generate passengers generates a passenger based on given probability on all the floors. See function.
            generatePassengers();
            //Increments through elevators, traveling, then loading and unloading one elevator at a time.
            for (int i = 0; i < elevatorNumber; i++) {
                elevatorList.get(i).travel();
                elevatorList.get(i).loadAndUnload();
            }
            /*Goes through array list of passengers bumped that need to re-request. See unload in elevators class for
            more info.*/
            requestAgain();
        }
        /* Once entire duration has elapsed, use time information gathered to print the spec requested report. */
        getFinalReport();
    }

    private void getFinalReport() {
        /* Requested final report from spec. */
        double averageTime = allTimes / numberOfPassengers;
        System.out.println("Average time: " + averageTime);
        System.out.println("Max time: " + max);
        System.out.println("Min time: " + min);
    }

    /* Generate the passengers on all floors. */
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
                numberOfPassengers += 1;
                toAddTo.addPass(pass);
                //See if passenger made it on the elevator or if it was at capacity.
                boolean wasAdded = addToElevator(pass);
                //If it was at capacity, send to array to be re-requested.
                if (wasAdded == false) {
                    passengersToRequestAgain.add(pass);
                }
            }
        }
        /* Was used for testing:
        //printWaitingPassengers();
         */
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

    /*Readds the passengers to the request queue because they got bumped.*/
    public void requestAgain() {
        //If no passengers in the queue return early
        if (passengersToRequestAgain.isEmpty()) {
            return;
        }
        //If there are, re-queue them with the addToElevator funciton.
        for (int i = 0; i <= passengersToRequestAgain.size(); i++) {
            Passenger pass = passengersToRequestAgain.remove(0);
            addToElevator(pass);
        }
    }


    /* Unused because it's for testing. Prints all the passengers waiting on all the floors (they came from generate
    passenger.*/

    /*
    public static void printWaitingPassengers() {
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

    /* Same with this function, it printed out the arrayList of passengers that needed to be re-requested. */
    /*
    public static void printNeedtoReq() {
        System.out.println("request again list \n");
        ArrayList<Passenger> temp2 = new ArrayList<>(passengersToRequestAgain);
        while (temp2.isEmpty() == false && temp2.get(0) != null) {
            temp2.remove(0).printPassenger();
        }
        System.out.println("End request again list.");
    }
    */


}
