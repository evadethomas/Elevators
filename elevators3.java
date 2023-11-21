import java.io.*;
import java.util.Properties;
/*Elevators3 class (the main class) either takes in a given properties file or it uses the built-in one. */
public class elevators3 {
    static String structures;
    static int floorNumber;
    static double passengers;
    static int elevatorNumber;
    static int elevatorCapacity;
    static int duration;

    public static void main(String[] args) throws Exception {
        //Creating a properties instance
        Properties prop = new Properties();
        //if no arguments given, use inherent property file.
        if (args.length < 1) {
            FileReader propFile = new FileReader("db.properties");
            prop.load(propFile);
        } else {
            /*If given an argument, read in the file and load properties from it. Try and catch catches any error
            messages */
            try {
                FileReader propFile = new FileReader(args[0]);
                prop.load(propFile);
            } catch (Exception e) {
                System.out.println("Error reading file");
            }
        }

        /* Setting variables based on property files. */
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

        /*Creating an instance of the simulation to actually run it.*/
        ElevatorSimulator simulation = new ElevatorSimulator();
        simulation.runSimulation();

    }
}
