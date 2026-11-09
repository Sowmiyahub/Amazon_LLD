
interface OrderState {
    void pay(Order order);
    void ship(Order order);
    void deliver(Order order);
    void cancel(Order order);
}

// Created State
class CreatedState implements OrderState {

    public void pay(Order order) {
        System.out.println("Payment successful!");
        order.setState(new PaidState());
    }

    public void ship(Order order) {
        System.out.println("Pay for the order first.");
    }

    public void deliver(Order order) {
        System.out.println("Order has not been shipped.");
    }

    public void cancel(Order order) {
        System.out.println("Order cancelled.");
        order.setState(new CancelledState());
    }
}

// Paid State
class PaidState implements OrderState {

    public void pay(Order order) {
        System.out.println("Order already paid.");
    }

    public void ship(Order order) {
        System.out.println("Order shipped!");
        order.setState(new ShippedState());
    }

    public void deliver(Order order) {
        System.out.println("Ship the order first.");
    }

    public void cancel(Order order) {
        System.out.println("Paid order cancelled and refund initiated.");
        order.setState(new CancelledState());
    }
}

// Shipped State
class ShippedState implements OrderState {

    public void pay(Order order) {
        System.out.println("Order already paid.");
    }

    public void ship(Order order) {
        System.out.println("Order already shipped.");
    }

    public void deliver(Order order) {
        System.out.println("Order delivered!");
        order.setState(new DeliveredState());
    }

    public void cancel(Order order) {
        System.out.println("Cannot cancel. Order already shipped.");
    }
}

// Delivered State
class DeliveredState implements OrderState {

    public void pay(Order order) {
        System.out.println("Order already completed.");
    }

    public void ship(Order order) {
        System.out.println("Order already delivered.");
    }

    public void deliver(Order order) {
        System.out.println("Order already delivered.");
    }

    public void cancel(Order order) {
        System.out.println("Cannot cancel a delivered order.");
    }
}

// Cancelled State
class CancelledState implements OrderState {

    public void pay(Order order) {
        System.out.println("Cannot pay for a cancelled order.");
    }

    public void ship(Order order) {
        System.out.println("Cannot ship a cancelled order.");
    }

    public void deliver(Order order) {
        System.out.println("Cannot deliver a cancelled order.");
    }

    public void cancel(Order order) {
        System.out.println("Order already cancelled.");
    }
}

// Context
class Order {

    private OrderState state;

    public Order() {
        state = new CreatedState();
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public void pay() {
        state.pay(this);
    }

    public void ship() {
        state.ship(this);
    }

    public void deliver() {
        state.deliver(this);
    }

    public void cancel() {
        state.cancel(this);
    }
}

// Main
public class Main {

    public static void main(String[] args) {

        Order order = new Order();

        order.ship();
        order.pay();
        order.pay();

        order.ship();
        order.cancel();

        order.deliver();
        order.cancel();
    }
}
