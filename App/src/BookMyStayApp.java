/**
 * Entry point demonstrating centralized inventory management using HashMap.
 * This version replaces scattered availability variables with a single
 * source of truth through the RoomInventory class.
 *
 * @author YourName
 * @version 2.0
 */
import java.util.HashMap;
import java.util.Map;

public class BookMyStayApp {

    public static void main(String[] args) {

        // Creating room objects (domain model remains unchanged)
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Initializing centralized inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType(singleRoom.getRoomType(), 5);
        inventory.addRoomType(doubleRoom.getRoomType(), 3);
        inventory.addRoomType(suiteRoom.getRoomType(), 2);

        // Displaying room details with availability from inventory
        System.out.println("=== Centralized Room Inventory ===");

        displayRoom(singleRoom, inventory);
        displayRoom(doubleRoom, inventory);
        displayRoom(suiteRoom, inventory);

        // Demonstrating update
        System.out.println("\nUpdating availability (Booking 1 Single Room)...");
        inventory.updateAvailability("Single Room", -1);

        // Display updated inventory
        System.out.println("\n=== Updated Inventory ===");
        displayRoom(singleRoom, inventory);

        System.out.println("\nApplication terminated.");
    }

    // Helper method to display room + availability
    private static void displayRoom(Room room, RoomInventory inventory) {
        room.displayDetails();
        int available = inventory.getAvailability(room.getRoomType());
        System.out.println("Available: " + available);
        System.out.println();
    }
}

/**
 * Dedicated class responsible for managing room availability.
 * Acts as a single source of truth using HashMap.
 */
class RoomInventory {

    private Map<String, Integer> availabilityMap;

    // Constructor initializes the HashMap
    public RoomInventory() {
        availabilityMap = new HashMap<>();
    }

    // Register a room type with initial availability
    public void addRoomType(String roomType, int count) {
        availabilityMap.put(roomType, count);
    }

    // Retrieve availability (O(1) lookup)
    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }

    // Controlled update to availability
    public void updateAvailability(String roomType, int change) {
        int current = getAvailability(roomType);
        availabilityMap.put(roomType, current + change);
    }

    // Display entire inventory
    public void displayInventory() {
        System.out.println("=== Inventory Snapshot ===");
        for (Map.Entry<String, Integer> entry : availabilityMap.entrySet()) {
            System.out.println(entry.getKey() + " -> Available: " + entry.getValue());
        }
    }
}

/**
 * Abstract Room class (unchanged domain model).
 */
abstract class Room {

    protected int beds;
    protected int size;
    protected double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public abstract String getRoomType();

    public void displayDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price per night: $" + price);
    }
}

/**
 * Concrete Room Types
 */
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 200, 50.0);
    }

    public String getRoomType() {
        return "Single Room";
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 350, 90.0);
    }

    public String getRoomType() {
        return "Double Room";
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 600, 200.0);
    }

    public String getRoomType() {
        return "Suite Room";
    }
}