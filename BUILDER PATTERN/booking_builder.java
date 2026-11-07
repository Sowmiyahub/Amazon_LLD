
import java.time.LocalDate;

class Booking {

    // 1. Fields
    private final String bookingId;
    private final String customerName;
    private final String roomNumber;
    private final LocalDate checkIn;
    private final LocalDate checkOut;

    // Optional fields
    private final int numberOfGuests;
    private final String specialRequests;
    private final boolean breakfastIncluded;

    // 2. Private constructor
    private Booking(Builder builder) {
        this.bookingId = builder.bookingId;
        this.customerName = builder.customerName;
        this.roomNumber = builder.roomNumber;
        this.checkIn = builder.checkIn;
        this.checkOut = builder.checkOut;
        this.numberOfGuests = builder.numberOfGuests;
        this.specialRequests = builder.specialRequests;
        this.breakfastIncluded = builder.breakfastIncluded;
    }

    // 3. Getters
    public String getBookingId() { return bookingId; }
    public String getCustomerName() { return customerName; }
    public String getRoomNumber() { return roomNumber; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public int getNumberOfGuests() { return numberOfGuests; }
    public String getSpecialRequests() { return specialRequests; }
    public boolean isBreakfastIncluded() { return breakfastIncluded; }

    // 4. Static nested Builder
    public static class Builder {

        // Mandatory fields
        private final String bookingId;
        private final String customerName;
        private final String roomNumber;
        private final LocalDate checkIn;
        private final LocalDate checkOut;

        // Optional fields with default values
        private int numberOfGuests = 1;
        private String specialRequests;
        private boolean breakfastIncluded = false;

        // 5. Builder constructor
        public Builder(String bookingId, String customerName,
                       String roomNumber, LocalDate checkIn,
                       LocalDate checkOut) {

            this.bookingId = bookingId;
            this.customerName = customerName;
            this.roomNumber = roomNumber;
            this.checkIn = checkIn;
            this.checkOut = checkOut;
        }

        // 6. Optional methods
        public Builder numberOfGuests(int numberOfGuests) {
            this.numberOfGuests = numberOfGuests;
            return this;
        }

        public Builder specialRequests(String specialRequests) {
            this.specialRequests = specialRequests;
            return this;
        }

        public Builder breakfastIncluded(boolean breakfastIncluded) {
            this.breakfastIncluded = breakfastIncluded;
            return this;
        }

        // 7. Build method
        public Booking build() {

            if (bookingId == null || bookingId.isBlank()) {
                throw new IllegalArgumentException("Booking ID required");
            }

            if (customerName == null || customerName.isBlank()) {
                throw new IllegalArgumentException("Customer name required");
            }

            if (roomNumber == null || roomNumber.isBlank()) {
                throw new IllegalArgumentException("Room number required");
            }

            if (checkIn == null || checkOut == null) {
                throw new IllegalArgumentException("Dates are required");
            }

            if (!checkOut.isAfter(checkIn)) {
                throw new IllegalArgumentException(
                    "Check-out must be after check-in"
                );
            }

            if (numberOfGuests <= 0) {
                throw new IllegalArgumentException(
                    "At least one guest is required"
                );
            }

            return new Booking(this);
        }
    }
}

public class Main {
    public static void main(String[] args) {

        Booking booking = new Booking.Builder(
            "B101",
            "Sowmi",
            "R201",
            LocalDate.of(2026, 11, 10),
            LocalDate.of(2026, 11, 13)
        )
        .numberOfGuests(2)
        .specialRequests("Need an extra bed")
        .breakfastIncluded(true)
        .build();

        System.out.println("Booking ID: " + booking.getBookingId());
        System.out.println("Customer: " + booking.getCustomerName());
        System.out.println("Room: " + booking.getRoomNumber());
        System.out.println("Check-in: " + booking.getCheckIn());
        System.out.println("Check-out: " + booking.getCheckOut());
        System.out.println("Guests: " + booking.getNumberOfGuests());
        System.out.println("Special Requests: " + booking.getSpecialRequests());
        System.out.println("Breakfast: " + booking.isBreakfastIncluded());
    }
}
