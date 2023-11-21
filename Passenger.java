import java.util.Random;
/*Passenger class instances are individual spawned passengers. */

public class Passenger extends elevators3 {
    /* Each passenger has start and end floats and times. Total time, is the duration they were on the elevator for.
    * Each passenger also has a direction. */
    int StartFloor;
    int EndFloor;
    int startTime;
    int endTime;
    int totalTime;
    boolean up;
    /*Constructor sers the StartTime, the start floor and end floor. See sets for more info. */
    public Passenger(int i, int currentTick) {
        setFloors(i);
        setStartTime(currentTick);
        totalTime = 0;
    }

    /*Start time will be the current tick (passed in when created). */
    public void setStartTime(int tick) {
        this.startTime = tick;
    }
    /**/
    /*Setting the floors will take in the current floor as the start floor (see gen passengers) and it will
    * randomize an endFloor. Will re-randomize if end-floor is same as start floor. */
    public void setFloors(int i) {
        Random rand = new Random();
        this.StartFloor = i;
        this.EndFloor = rand.nextInt(floorNumber);
        while (this.StartFloor == this.EndFloor) {
            this.EndFloor = rand.nextInt(floorNumber);
        }
        if (this.StartFloor < this.EndFloor) {
            this.up = true;
        }
    }
    /* SetsEndTime when passenger is dropped off. */
    public void setEndTime(int i) {
        this.endTime = i;

    }
/* Again, used for testing, prints out the different passengers.
    public void printPassenger() {
        System.out.println();
        System.out.println("Passenger:\nStart Floor: " + StartFloor + "\nEnd Floor: " + EndFloor);
        System.out.println("Start Time: " + startTime + "\nEndTime: " + endTime + "\n");
    }

 */
/* Some getters and setters */
    public int getEndFloor() {
        return EndFloor;
    }

    public int getTotalTime() {
        return this.endTime - this.startTime;
    }
}
