/**
 * BookMyStayApp is the entry point for the Hotel Booking application.
 * It demonstrates how a Java program starts execution and prints output to the console.
 *
 * The application displays a welcome message along with the application
 * name and version information.
 *
 * @author YourName
 * @version 1.0
 */
public class BookMyStayApp {

    /**
     * The main method is the entry point of the application.
     * The JVM invokes this method to start program execution.
     *
     * @param args Command-line arguments (not used in this application)
     */
    public static void main(String[] args) {

        // Application name and version (String literals)
        String appName = "BookMyStay - Hotel Booking System";
        String version = "v1.0";

        // Printing welcome message to console
        System.out.println("Welcome to " + appName);
        System.out.println("Application Version: " + version);

        // Indicating application start and end flow
        System.out.println("Application started successfully.");
        System.out.println("Application terminated.");
    }
}