
/**
 * Entry point demonstrating booking request intake using a Queue.
 * Requests are collected and stored in arrival order without
 * modifying inventory or performing allocation.
 *
 * @author YourName
 * @version 4.0
 */
import java.util.*;

public class BookMyStayApp {

    public static void main(String[] args) {

        // Booking request queue (FIFO)
        BookingRequestQueue requestQueue = new BookingRequestQueue();

        // Simulating guest booking requests
        requestQueue.addRequest(new Reservation("Alice", "Single Room"));
        requestQueue.addRequest(new Reservation("Bob", "Double Room"));
        requestQueue.addRequest(new Reservation("Charlie", "Suite Room"));
        requestQueue.addRequest(new Reservation("Diana", "Single Room"));

        // Display queued requests (no processing yet)
        System.out.println("=== Booking Requests Queue (FIFO Order) ===");
        requestQueue.displayQueue();

        System.out.println("\nAll requests are queued. No allocation performed.");
    }
}

/**
 * Represents a guest booking request.
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

    public void display() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

/**
 * Manages booking requests using a Queue (FIFO).
 */
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add request to queue (enqueue)
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // View all queued requests (without removing)
    public void displayQueue() {
        for (Reservation reservation : queue) {
            reservation.display();
        }
    }

    // Peek next request (no removal)
    public Reservation peekNext() {
        return queue.peek();
    }

    // Remove next request (for future processing stage)
    public Reservation processNext() {
        return queue.poll();
    }
}