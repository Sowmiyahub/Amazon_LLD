
import java.util.*;

// 1. Observer Interface
interface Observer {
    void update(String video);
}

// 2. Concrete Observer 1
class EmailSubscriber implements Observer {

    private String name;

    public EmailSubscriber(String name) {
        this.name = name;
    }

    public void update(String video) {
        System.out.println(name + " received Email: New video - " + video);
    }
}

// 3. Concrete Observer 2
class MobileSubscriber implements Observer {

    private String name;

    public MobileSubscriber(String name) {
        this.name = name;
    }

    public void update(String video) {
        System.out.println(name + " received Mobile Notification: " + video);
    }
}

// 4. Subject Interface
interface Subject {
    void subscribe(Observer observer);
    void unsubscribe(Observer observer);
    void notifySubscribers(String video);
}

// 5. Concrete Subject
class YouTubeChannel implements Subject {

    private List<Observer> subscribers = new ArrayList<>();

    public void subscribe(Observer observer) {
        subscribers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        subscribers.remove(observer);
    }

    public void notifySubscribers(String video) {
        for (Observer observer : subscribers) {
            observer.update(video);
        }
    }

    public void uploadVideo(String video) {
        System.out.println("\nNew video uploaded: " + video);
        notifySubscribers(video);
    }
}

// 6. Main
public class Main {

    public static void main(String[] args) {

        YouTubeChannel channel = new YouTubeChannel();

        Observer user1 = new EmailSubscriber("Sowmi");
        Observer user2 = new MobileSubscriber("Rahul");
        Observer user3 = new EmailSubscriber("Priya");

        channel.subscribe(user1);
        channel.subscribe(user2);
        channel.subscribe(user3);

        channel.uploadVideo("Java Design Patterns");

        // User unsubscribes
        channel.unsubscribe(user2);

        channel.uploadVideo("System Design for SDEs");
    }
}
