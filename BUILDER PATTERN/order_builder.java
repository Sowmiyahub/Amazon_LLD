
//Order creation and order lifecycle are different responsibilities.
//  Builder creates the order; State can manage its lifecycle, such as PLACED, CONFIRMED,
//  SHIPPED and DELIVERED.
import java.util.*;

class Order {

    // 1. Fields
    private final String orderId;
    private final String customerId;
    private final List<String> items;
    private final String deliveryAddress;

    // Optional fields
    private final String couponCode;
    private final String giftMessage;
    private final String deliveryInstructions;

    // 2. Private constructor
    private Order(Builder builder) {
        this.orderId = builder.orderId;
        this.customerId = builder.customerId;
        this.items = Collections.unmodifiableList(
            new ArrayList<>(builder.items)
        );
        this.deliveryAddress = builder.deliveryAddress;
        this.couponCode = builder.couponCode;
        this.giftMessage = builder.giftMessage;
        this.deliveryInstructions = builder.deliveryInstructions;
    }

    // 3. Getters
    public String getOrderId() { return orderId; }
    public String getCustomerId() { return customerId; }
    public List<String> getItems() { return items; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public String getCouponCode() { return couponCode; }
    public String getGiftMessage() { return giftMessage; }
    public String getDeliveryInstructions() { return deliveryInstructions; }

    // 4. Static nested Builder
    public static class Builder {

        // Mandatory fields
        private final String orderId;
        private final String customerId;
        private final List<String> items;
        private final String deliveryAddress;

        // Optional fields
        private String couponCode;
        private String giftMessage;
        private String deliveryInstructions;

        // 5. Builder constructor
        public Builder(String orderId, String customerId,
                       List<String> items, String deliveryAddress) {
            this.orderId = orderId;
            this.customerId = customerId;
            this.items = items == null
                    ? null
                    : new ArrayList<>(items);
            this.deliveryAddress = deliveryAddress;
        }

        // 6. Optional methods
        public Builder couponCode(String couponCode) {
            this.couponCode = couponCode;
            return this;
        }

        public Builder giftMessage(String giftMessage) {
            this.giftMessage = giftMessage;
            return this;
        }

        public Builder deliveryInstructions(String instructions) {
            this.deliveryInstructions = instructions;
            return this;
        }

        // 7. Build method
        public Order build() {

            if (orderId == null || orderId.isBlank()) {
                throw new IllegalArgumentException("Order ID required");
            }

            if (customerId == null || customerId.isBlank()) {
                throw new IllegalArgumentException("Customer ID required");
            }

            if (items == null || items.isEmpty()) {
                throw new IllegalArgumentException("Order must contain items");
            }

            if (items.stream().anyMatch(
                    item -> item == null || item.isBlank())) {
                throw new IllegalArgumentException("Invalid order item");
            }

            if (deliveryAddress == null || deliveryAddress.isBlank()) {
                throw new IllegalArgumentException("Delivery address required");
            }

            return new Order(this);
        }
    }
}

public class Main {
    public static void main(String[] args) {

        List<String> items = Arrays.asList(
            "MacBook",
            "Mouse",
            "Keyboard"
        );

        Order order = new Order.Builder(
            "ORD101",
            "CUST101",
            items,
            "Chennai, Tamil Nadu"
        )
        .couponCode("SAVE20")
        .giftMessage("Happy Birthday!")
        .deliveryInstructions("Deliver at the doorstep")
        .build();

        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Customer: " + order.getCustomerId());
        System.out.println("Items: " + order.getItems());
        System.out.println("Address: " + order.getDeliveryAddress());
        System.out.println("Coupon: " + order.getCouponCode());
        System.out.println("Gift: " + order.getGiftMessage());
        System.out.println("Instructions: " + order.getDeliveryInstructions());
    }
}
