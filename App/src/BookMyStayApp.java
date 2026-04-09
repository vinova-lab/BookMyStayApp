
/**
 * Entry point demonstrating booking confirmation with safe allocation.
 * Requests are processed from a queue, rooms are assigned uniquely,
 * and inventory is updated atomically.
 *
 * @author YourName
 * @version 5.0
 */
import java.util.*;

public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Double Room", 1);

        // Initialize booking queue
        BookingRequestQueue requestQueue = new BookingRequestQueue();
        requestQueue.addRequest(new Reservation("Alice", "Single Room"));
        requestQueue.addRequest(new Reservation("Bob", "Single Room"));
        requestQueue.addRequest(new Reservation("Charlie", "Single Room")); // Should fail
        requestQueue.addRequest(new Reservation("Diana", "Double Room"));

        // Booking service
        BookingService bookingService = new BookingService(inventory);

        System.out.println("\n=== Processing Booking Requests ===");

        while (requestQueue.hasRequests()) {
            Reservation request = requestQueue.processNext();
            bookingService.processBooking(request);
        }

        System.out.println("\n=== Final Allocation State ===");
        bookingService.displayAllocations();
    }
}

/**
 * Booking Service: Handles safe allocation and prevents double-booking.
 */
class BookingService {

    private RoomInventory inventory;

    // Track allocated room IDs per room type
    private Map<String, Set<String>> allocatedRooms;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRooms = new HashMap<>();
    }

    public void processBooking(Reservation reservation) {

        String roomType = reservation.getRoomType();
        String guest = reservation.getGuestName();

        int available = inventory.getAvailability(roomType);

        // Check availability
        if (available <= 0) {
            System.out.println("Booking FAILED for " + guest + " (No rooms available: " + roomType + ")");
            return;
        }

        // Generate unique room ID
        String roomId = generateRoomId(roomType);

        // Ensure set exists
        allocatedRooms.putIfAbsent(roomType, new HashSet<>());

        Set<String> assignedSet = allocatedRooms.get(roomType);

        // Safety check (Set guarantees uniqueness)
        if (assignedSet.contains(roomId)) {
            System.out.println("ERROR: Duplicate room detected!");
            return;
        }

        // --- Atomic Logical Operation ---
        assignedSet.add(roomId);                 // Assign room
        inventory.updateAvailability(roomType, -1); // Update inventory

        // Confirmation
        System.out.println("Booking CONFIRMED for " + guest +
                " | Room Type: " + roomType +
                " | Room ID: " + roomId);
    }

    // Simple unique ID generator
    private String generateRoomId(String roomType) {
        return roomType.substring(0, 2).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 5);
    }

    // Display allocation state
    public void displayAllocations() {
        for (Map.Entry<String, Set<String>> entry : allocatedRooms.entrySet()) {
            System.out.println(entry.getKey() + " -> Allocated Rooms: " + entry.getValue());
        }
    }
}

/**
 * Inventory Service: Maintains room availability.
 */
class RoomInventory {

    private Map<String, Integer> availabilityMap;

    public RoomInventory() {
        availabilityMap = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        availabilityMap.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int change) {
        int current = getAvailability(roomType);
        availabilityMap.put(roomType, current + change);
    }
}

/**
 * Reservation (Booking Request)
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**
 * Booking Request Queue (FIFO)
 */
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    public Reservation processNext() {
        return queue.poll();
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}