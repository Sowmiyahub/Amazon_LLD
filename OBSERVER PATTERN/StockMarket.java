
import java.util.*;

// 1. Observer Interface
interface Observer {
    void update(String stockName, double price);
}

// 2. Concrete Observers
class EmailInvestor implements Observer {
    private String name;

    public EmailInvestor(String name) {
        this.name = name;
    }

    public void update(String stockName, double price) {
        System.out.println(name + " received an Email - Stock: "
                + stockName + ", Price: " + price);
    }
}

class MobileInvestor implements Observer {
    private String name;

    public MobileInvestor(String name) {
        this.name = name;
    }

    public void update(String stockName, double price) {
        System.out.println(name + " received a Mobile Notification - Stock: "
                + stockName + ", Price: " + price);
    }
}

// 3. Subject Interface
interface Subject {
    void subscribe(Observer observer);
    void unsubscribe(Observer observer);
    void notifySubscribers();
}

// 4. Concrete Subject
class Stock implements Subject {

    private List<Observer> subscribers = new ArrayList<>();

    private String stockName;
    private double price;

    public Stock(String stockName, double price) {
        this.stockName = stockName;
        this.price = price;
    }

    public void subscribe(Observer observer) {
        subscribers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        subscribers.remove(observer);
    }

    public void notifySubscribers() {
        for (Observer observer : subscribers) {
            observer.update(stockName, price);
        }
    }

    public void setPrice(double price) {
        this.price = price;

        System.out.println("\n" + stockName
                + " price changed to: " + price);

        notifySubscribers();
    }
}

// 5. Main Class
public class Main {
    public static void main(String[] args) {

        Stock stock = new Stock("TCS", 3500.0);

        Observer investor1 = new EmailInvestor("Sowmi");
        Observer investor2 = new MobileInvestor("Rahul");
        Observer investor3 = new EmailInvestor("Priya");

        // Subscribe investors
        stock.subscribe(investor1);
        stock.subscribe(investor2);
        stock.subscribe(investor3);

        // First price update
        stock.setPrice(3600.0);

        // Unsubscribe Rahul
        stock.unsubscribe(investor2);

        // Second price update
        stock.setPrice(3700.0);
    }
}
