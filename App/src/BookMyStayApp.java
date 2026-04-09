/**
 * Entry point for demonstrating Room modeling using abstraction and inheritance.
 * This program creates different room types and displays their details
 * along with availability information.
 *
 * @author YourName
 * @version 1.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        // Creating room objects using polymorphism
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static availability (simple variables)
        int singleRoomAvailable = 5;
        int doubleRoomAvailable = 3;
        int suiteRoomAvailable = 2;

        // Displaying room details and availability
        System.out.println("=== Room Details and Availability ===");

        singleRoom.displayDetails();
        System.out.println("Available: " + singleRoomAvailable);
        System.out.println();

        doubleRoom.displayDetails();
        System.out.println("Available: " + doubleRoomAvailable);
        System.out.println();

        suiteRoom.displayDetails();
        System.out.println("Available: " + suiteRoomAvailable);

        System.out.println("\nApplication terminated.");
    }
}

/**
 * Abstract class representing a general Room.
 * Defines common attributes and enforces structure.
 */
abstract class Room {

    protected int beds;
    protected int size; // in square feet
    protected double price;

    // Constructor
    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    // Abstract method (must be implemented by subclasses)
    public abstract String getRoomType();

    // Common method
    public void displayDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price per night: $" + price);
    }
}

/**
 * Concrete class representing a Single Room.
 */
class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 200, 50.0);
    }

    @Override
    public String getRoomType() {
        return "Single Room";
    }
}

/**
 * Concrete class representing a Double Room.
 */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 350, 90.0);
    }

    @Override
    public String getRoomType() {
        return "Double Room";
    }
}

/**
 * Concrete class representing a Suite Room.
 */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 600, 200.0);
    }

    @Override
    public String getRoomType() {
        return "Suite Room";
    }
}