
/**
 * Entry point demonstrating add-on services for reservations.
 * Services are attached to reservations without modifying
 * booking or inventory logic.
 *
 * @author YourName
 * @version 6.0
 */
import java.util.*;

public class BookMyStayApp {

    public static void main(String[] args) {

        // Simulated confirmed reservation IDs
        String res1 = "RES-101";
        String res2 = "RES-102";

        // Add-on services
        AddOnService breakfast = new AddOnService("Breakfast", 10.0);
        AddOnService wifi = new AddOnService("WiFi", 5.0);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 25.0);

        // Service manager
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Guest selects services
        serviceManager.addService(res1, breakfast);
        serviceManager.addService(res1, wifi);

        serviceManager.addService(res2, airportPickup);
        serviceManager.addService(res2, breakfast);

        // Display services and total cost
        System.out.println("=== Add-On Services for Reservations ===\n");

        serviceManager.displayServices(res1);
        System.out.println();

        serviceManager.displayServices(res2);

        System.out.println("\nCore booking and inventory remain unchanged.");
    }
}

/**
 * Represents an optional add-on service.
 */
class AddOnService {

    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }
}

/**
 * Manages services associated with reservations.
 */
class AddOnServiceManager {

    // Map<ReservationID, List of Services>
    private Map<String, List<AddOnService>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to a reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added " + service.getServiceName() +
                " to Reservation " + reservationId);
    }

    // Display services + total cost
    public void displayServices(String reservationId) {

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services for Reservation " + reservationId);
            return;
        }

        double totalCost = 0;

        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Selected Services:");

        for (AddOnService service : services) {
            System.out.println("- " + service.getServiceName() +
                    " ($" + service.getPrice() + ")");
            totalCost += service.getPrice();
        }

        System.out.println("Total Add-On Cost: $" + totalCost);
    }
}