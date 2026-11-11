
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Observer customer = new Customer("Sowmi");
        Observer restaurant = new Restaurant("Hotel Sangeetha");
        Observer deliveryPartner = new DeliveryPartner("Ragul");

        FoodOrder order = new FoodOrder("101");

        order.subscribe(customer);
        order.subscribe(restaurant);
        order.subscribe(deliveryPartner);

        order.setStatus("ORDER_CREATED");
        order.setStatus("ORDER_ACCEPTED");

        order.unsubscribe(deliveryPartner);

        order.setStatus("ORDER_DONE");
    }
}

// Observer Interface
interface Observer {
    void update(String orderId, String status);
}

// Concrete Observers
class Customer implements Observer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public void update(String orderId, String status) {
        System.out.println("Customer " + name
                + " received a new notification: Your order #"
                + orderId + " is " + status);
    }
}

class Restaurant implements Observer {
    private String name;

    public Restaurant(String name) {
        this.name = name;
    }

    public void update(String orderId, String status) {
        System.out.println("Restaurant " + name
                + " received a new notification: New order #"
                + orderId + ", Status: " + status);
    }
}

class DeliveryPartner implements Observer {
    private String name;

    public DeliveryPartner(String name) {
        this.name = name;
    }

    public void update(String orderId, String status) {
        System.out.println("Delivery Partner " + name
                + " received a new notification: Order #"
                + orderId + ", Status: " + status);
    }
}

// Subject Interface
interface Subject {
    void subscribe(Observer observer);
    void unsubscribe(Observer observer);
    void notifySubscribers();
}

// Concrete Subject
class FoodOrder implements Subject {

    private String orderId;
    private String status;

    private List<Observer> subscribers = new ArrayList<>();

    public FoodOrder(String orderId) {
        this.orderId = orderId;
        this.status = "ORDER_CREATED";
    }

    public void subscribe(Observer observer) {
        subscribers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        subscribers.remove(observer);
    }

    public void notifySubscribers() {
        for (Observer observer : subscribers) {
            observer.update(orderId, status);
        }
    }

    public void setStatus(String status) {
        this.status = status;

        System.out.println("\nOrder #" + orderId
                + " status has been changed to " + status);

        notifySubscribers();
    }
}
