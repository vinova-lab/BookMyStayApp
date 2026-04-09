/**
 * Entry point demonstrating read-only room search functionality.
 * Guests can view available rooms without modifying system state.
 *
 * @author YourName
 * @version 3.0
 */
import java.util.*;

public class BookMyStayApp {

    public static void main(String[] args) {

        // Room objects (domain model)
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Centralized inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType(singleRoom.getRoomType(), 5);
        inventory.addRoomType(doubleRoom.getRoomType(), 0); // Not available
        inventory.addRoomType(suiteRoom.getRoomType(), 2);

        // Search service (read-only)
        RoomSearchService searchService = new RoomSearchService();

        // Guest initiates search
        System.out.println("=== Available Rooms ===");
        searchService.searchAvailableRooms(
                Arrays.asList(singleRoom, doubleRoom, suiteRoom),
                inventory
        );

        System.out.println("\nSearch completed. No changes made to inventory.");
    }
}

/**
 * Search service responsible for read-only operations.
 */
class RoomSearchService {

    public void searchAvailableRooms(List<Room> rooms, RoomInventory inventory) {

        for (Room room : rooms) {

            // Read-only access to inventory
            int available = inventory.getAvailability(room.getRoomType());

            // Defensive check: filter unavailable rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println();
            }
        }
    }
}

/**
 * Inventory class (state holder).
 */
class RoomInventory {

    private Map<String, Integer> availabilityMap;

    public RoomInventory() {
        availabilityMap = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        availabilityMap.put(roomType, count);
    }

    // Read-only access
    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }

    // (Not used in search)
    public void updateAvailability(String roomType, int change) {
        int current = getAvailability(roomType);
        availabilityMap.put(roomType, current + change);
    }
}

/**
 * Abstract Room class.
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