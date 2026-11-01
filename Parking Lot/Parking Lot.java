
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.*;

// 1. Main parking lot system
class ParkingLot {
    private final List<ParkingFloor> floors;
    private final Map<String, Ticket> activeTickets;

    public ParkingLot(List<ParkingFloor> floors) {
        this.floors = new ArrayList<>(floors);
        this.activeTickets = new HashMap<>();
    }

    public Ticket parkVehicle(Vehicle vehicle) {
        for (ParkingFloor floor : floors) {
            ParkingSpot spot = floor.findAvailableSpot(vehicle.getType());

            if (spot != null && spot.parkVehicle(vehicle)) {
                PricingStrategy pricing =
                        PricingStrategyFactory.getStrategy(vehicle.getType());

                Ticket ticket = new Ticket(vehicle, spot, pricing);
                activeTickets.put(ticket.getId(), ticket);
                return ticket;
            }
        }

        return null;
    }

    public double unparkVehicle(String ticketId) {
        Ticket ticket = activeTickets.remove(ticketId);

        if (ticket == null) {
            throw new IllegalArgumentException("Invalid ticket");
        }

        ticket.setExitTime(LocalDateTime.now());
        ticket.getSpot().removeVehicle();

        return ticket.calculateFee();
    }
}

// 2. Parking floor
class ParkingFloor {
    private final int floorNumber;
    private final List<ParkingSpot> spots;

    public ParkingFloor(int floorNumber, List<ParkingSpot> spots) {
        this.floorNumber = floorNumber;
        this.spots = new ArrayList<>(spots);
    }

    public ParkingSpot findAvailableSpot(ParkingSpotType type) {
        for (ParkingSpot spot : spots) {
            if (spot.isAvailable() && spot.getType() == type) {
                return spot;
            }
        }
        return null;
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}

// 3. Parking spot
class ParkingSpot {
    private final String spotId;
    private final ParkingSpotType type;
    private Vehicle vehicle;

    public ParkingSpot(String spotId, ParkingSpotType type) {
        this.spotId = spotId;
        this.type = type;
    }

    public boolean isAvailable() {
        return vehicle == null;
    }

    public boolean parkVehicle(Vehicle vehicle) {
        if (!isAvailable() || vehicle.getType() != type) {
            return false;
        }

        this.vehicle = vehicle;
        return true;
    }

    public void removeVehicle() {
        if (vehicle == null) {
            throw new IllegalStateException("Spot is already empty");
        }
        vehicle = null;
    }

    public ParkingSpotType getType() {
        return type;
    }

    public String getSpotId() {
        return spotId;
    }
}

// 4. Vehicle
class Vehicle {
    private final String licensePlate;
    private final ParkingSpotType type;

    public Vehicle(String licensePlate, ParkingSpotType type) {
        this.licensePlate = licensePlate;
        this.type = type;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public ParkingSpotType getType() {
        return type;
    }
}

// 5. Pricing strategy interface
interface PricingStrategy {
    double getHourlyRate();
}

// 6. Pricing implementations
class CarPricing implements PricingStrategy {
    public double getHourlyRate() {
        return 30.0;
    }
}

class BikePricing implements PricingStrategy {
    public double getHourlyRate() {
        return 15.0;
    }
}

class TruckPricing implements PricingStrategy {
    public double getHourlyRate() {
        return 50.0;
    }
}

// 7. Factory to select the pricing strategy
class PricingStrategyFactory {
    public static PricingStrategy getStrategy(ParkingSpotType type) {
        switch (type) {
            case CAR:
                return new CarPricing();
            case BIKE:
                return new BikePricing();
            case TRUCK:
                return new TruckPricing();
            default:
                throw new IllegalArgumentException("Unknown vehicle type");
        }
    }
}

// 8. Parking ticket
class Ticket {
    private final String id;
    private final Vehicle vehicle;
    private final ParkingSpot spot;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private final PricingStrategy pricingStrategy;

    public Ticket(Vehicle vehicle, ParkingSpot spot,
                  PricingStrategy pricingStrategy) {
        this.id = UUID.randomUUID().toString();
        this.vehicle = vehicle;
        this.spot = spot;
        this.pricingStrategy = pricingStrategy;
        this.entryTime = LocalDateTime.now();
    }

    public double calculateFee() {
        LocalDateTime end = (exitTime == null)
                ? LocalDateTime.now() : exitTime;

        long minutes = Duration.between(entryTime, end).toMinutes();
        long hours = Math.max(1, (minutes + 59) / 60);

        return hours * pricingStrategy.getHourlyRate();
    }

    public String getId() {
        return id;
    }

    public ParkingSpot getSpot() {
        return spot;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }
}

// 9. Payment strategy interface
interface PaymentStrategy {
    void pay(double amount);
}

// 10. Payment implementations
class UpiPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid Rs. " + amount + " using UPI");
    }
}

class CardPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid Rs. " + amount + " using Card");
    }
}

class CashPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid Rs. " + amount + " using Cash");
    }
}

// 11. Supported parking spot types
enum ParkingSpotType {
    BIKE,
    CAR,
    TRUCK
}

// 12. Example usage
class Main {
    public static void main(String[] args) {
        ParkingSpot carSpot =
                new ParkingSpot("A101", ParkingSpotType.CAR);

        ParkingSpot bikeSpot =
                new ParkingSpot("A102", ParkingSpotType.BIKE);

        ParkingFloor floor = new ParkingFloor(
                1, Arrays.asList(carSpot, bikeSpot)
        );

        ParkingLot lot = new ParkingLot(Arrays.asList(floor));

        Vehicle car = new Vehicle(
                "TN01AB1234", ParkingSpotType.CAR
        );

        Ticket ticket = lot.parkVehicle(car);

        if (ticket == null) {
            System.out.println("No available parking spot");
            return;
        }

        System.out.println("Ticket ID: " + ticket.getId());

        double fee = lot.unparkVehicle(ticket.getId());
        System.out.println("Parking fee: Rs. " + fee);

        PaymentStrategy payment = new UpiPayment();
        payment.pay(fee);
    }
}
