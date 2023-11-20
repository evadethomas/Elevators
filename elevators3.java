import java.util.*;
import java.io.*;
import java.util.Properties;
public class elevators3 {
    static String structures;
    static int floorNumber;
    static double passengers;
    static int elevatorNumber;
    static int elevatorCapacity;
    static int duration;

    public static void main(String[] args) throws Exception {
        // get all the system properties
        Properties prop = new Properties();
        if (args.length < 1) {
            FileReader propFile = new FileReader("db.properties");
            prop.load(propFile);
        } else {
            try {
                FileReader propFile = new FileReader(args[0]);
                prop.load(propFile);
            } catch (Exception e) {
                System.out.println("Error reading file");
            }
        }

        structures = prop.getProperty("structures");
        floorNumber = Integer.parseInt(prop.getProperty("floors"));
        elevatorNumber = Integer.valueOf(prop.getProperty("elevators"));
        passengers = Double.valueOf(prop.getProperty("passengers"));
        elevatorCapacity = Integer.valueOf(prop.getProperty("elevatorCapacity"));
        duration = Integer.valueOf(prop.getProperty("duration"));

        /*
        Commented out prints for testing:
        System.out.println(structures+ " " + floorNumber+ " " + elevatorNumber+ " " + passengers+ " " + elevatorCapacity+ " " + duration);
        */

        ElevatorSimulator simulation = new ElevatorSimulator();
        simulation.runSimulation();


    }
}
