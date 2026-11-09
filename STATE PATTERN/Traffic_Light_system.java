
interface TrafficLightState {
    void next(TrafficLight light);
    void display();
}

// Red State
class RedState implements TrafficLightState {

    public void next(TrafficLight light) {
        System.out.println("Changing Red to Green...");
        light.setState(new GreenState());
    }

    public void display() {
        System.out.println("RED - Stop!");
    }
}

// Green State
class GreenState implements TrafficLightState {

    public void next(TrafficLight light) {
        System.out.println("Changing Green to Yellow...");
        light.setState(new YellowState());
    }

    public void display() {
        System.out.println("GREEN - Go!");
    }
}

// Yellow State
class YellowState implements TrafficLightState {

    public void next(TrafficLight light) {
        System.out.println("Changing Yellow to Red...");
        light.setState(new RedState());
    }

    public void display() {
        System.out.println("YELLOW - Get Ready!");
    }
}

// Context
class TrafficLight {

    private TrafficLightState state;

    public TrafficLight() {
        state = new RedState();
    }

    public void setState(TrafficLightState state) {
        this.state = state;
    }

    public void next() {
        state.next(this);
    }

    public void display() {
        state.display();
    }
}

// Main
public class Main {

    public static void main(String[] args) {

        TrafficLight light = new TrafficLight();

        light.display();
        light.next();

        light.display();
        light.next();

        light.display();
        light.next();

        light.display();
    }
}
