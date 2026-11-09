
interface ElevatorState {
    void moveUp(Elevator elevator);
    void moveDown(Elevator elevator);
    void openDoor(Elevator elevator);
    void closeDoor(Elevator elevator);
}

// 1. Idle State
class IdleState implements ElevatorState {

    public void moveUp(Elevator elevator) {
        System.out.println("Moving up...");
        elevator.setState(new MovingUpState());
    }

    public void moveDown(Elevator elevator) {
        System.out.println("Moving down...");
        elevator.setState(new MovingDownState());
    }

    public void openDoor(Elevator elevator) {
        System.out.println("Opening door...");
        elevator.setState(new DoorOpenState());
    }

    public void closeDoor(Elevator elevator) {
        System.out.println("Door already closed.");
    }
}

// 2. Moving Up State
class MovingUpState implements ElevatorState {

    public void moveUp(Elevator elevator) {
        System.out.println("Continue moving up.");
    }

    public void moveDown(Elevator elevator) {
        System.out.println("Cannot reverse direction immediately.");
    }

    public void openDoor(Elevator elevator) {
        System.out.println("Cannot open while moving.");
    }

    public void closeDoor(Elevator elevator) {
        System.out.println("Doors are already closed.");
    }
}

// 3. Moving Down State
class MovingDownState implements ElevatorState {

    public void moveUp(Elevator elevator) {
        System.out.println("Cannot reverse direction immediately.");
    }

    public void moveDown(Elevator elevator) {
        System.out.println("Continue moving down.");
    }

    public void openDoor(Elevator elevator) {
        System.out.println("Cannot open while moving.");
    }

    public void closeDoor(Elevator elevator) {
        System.out.println("Doors are already closed.");
    }
}

// 4. Door Open State
class DoorOpenState implements ElevatorState {

    public void moveUp(Elevator elevator) {
        System.out.println("Cannot move while doors are open.");
    }

    public void moveDown(Elevator elevator) {
        System.out.println("Cannot move while doors are open.");
    }

    public void openDoor(Elevator elevator) {
        System.out.println("Doors are already open.");
    }

    public void closeDoor(Elevator elevator) {
        System.out.println("Closing doors...");
        elevator.setState(new IdleState());
    }
}

// 5. Context Class
class Elevator {

    private ElevatorState state;

    public Elevator() {
        this.state = new IdleState();
    }

    public void setState(ElevatorState state) {
        this.state = state;
    }

    public void moveUp() {
        state.moveUp(this);
    }

    public void moveDown() {
        state.moveDown(this);
    }

    public void openDoor() {
        state.openDoor(this);
    }

    public void closeDoor() {
        state.closeDoor(this);
    }
}

// 6. Main Class
public class Main {

    public static void main(String[] args) {

        Elevator elevator = new Elevator();

        elevator.openDoor();
        elevator.moveUp();
        elevator.closeDoor();

        elevator.moveUp();
        elevator.openDoor();
        elevator.moveDown();

        elevator.moveDown();
        elevator.closeDoor();

        elevator.openDoor();
        elevator.closeDoor();

        elevator.moveDown();
    }
}
