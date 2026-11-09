
interface ATMState {
    void insertCard(ATM atm);
    void enterPin(ATM atm);
    void withdraw(ATM atm);
    void ejectCard(ATM atm);
    void dispenseCash(ATM atm);
}

// 1. Idle State
class IdleState implements ATMState {

    public void insertCard(ATM atm) {
        System.out.println("Inserting card...");
        atm.setState(new HasCardState());
    }

    public void enterPin(ATM atm) {
        System.out.println("Please insert card first.");
    }

    public void withdraw(ATM atm) {
        System.out.println("Please insert card first.");
    }

    public void ejectCard(ATM atm) {
        System.out.println("No card available to eject.");
    }

    public void dispenseCash(ATM atm) {
        System.out.println("No transaction in progress.");
    }
}

// 2. Has Card State
class HasCardState implements ATMState {

    public void insertCard(ATM atm) {
        System.out.println("Card already inserted.");
    }

    public void enterPin(ATM atm) {
        System.out.println("Entering PIN...");
        System.out.println("PIN verified successfully!");
        atm.setState(new AuthenticatedState());
    }

    public void withdraw(ATM atm) {
        System.out.println("Please authenticate PIN first.");
    }

    public void ejectCard(ATM atm) {
        System.out.println("Ejecting card...");
        atm.setState(new IdleState());
    }

    public void dispenseCash(ATM atm) {
        System.out.println("No withdrawal requested.");
    }
}

// 3. Authenticated State
class AuthenticatedState implements ATMState {

    public void insertCard(ATM atm) {
        System.out.println("Card already inserted.");
    }

    public void enterPin(ATM atm) {
        System.out.println("Already authenticated.");
    }

    public void withdraw(ATM atm) {
        System.out.println("Processing withdrawal...");
        atm.setState(new DispensingState());
    }

    public void ejectCard(ATM atm) {
        System.out.println("Ejecting card...");
        atm.setState(new IdleState());
    }

    public void dispenseCash(ATM atm) {
        System.out.println("No cash dispensing in progress.");
    }
}

// 4. Dispensing State
class DispensingState implements ATMState {

    public void insertCard(ATM atm) {
        System.out.println("Transaction in progress. Cannot insert another card.");
    }

    public void enterPin(ATM atm) {
        System.out.println("Transaction in progress. Cannot enter PIN.");
    }

    public void withdraw(ATM atm) {
        System.out.println("Withdrawal already in progress.");
    }

    public void ejectCard(ATM atm) {
        System.out.println("Please wait until cash dispensing completes.");
    }

    public void dispenseCash(ATM atm) {
        System.out.println("Cash dispensed successfully!");
        System.out.println("Please collect your cash.");
        atm.setState(new AuthenticatedState());
    }
}

// 5. Context Class
class ATM {

    private ATMState state;

    public ATM() {
        state = new IdleState();
    }

    public void setState(ATMState state) {
        this.state = state;
    }

    public void insertCard() {
        state.insertCard(this);
    }

    public void enterPin() {
        state.enterPin(this);
    }

    public void withdraw() {
        state.withdraw(this);
    }

    public void ejectCard() {
        state.ejectCard(this);
    }

    public void dispenseCash() {
        state.dispenseCash(this);
    }
}

// 6. Main Class
public class Main {

    public static void main(String[] args) {

        ATM atm = new ATM();

        atm.insertCard();
        atm.enterPin();

        atm.withdraw();
        atm.dispenseCash();

        // Second withdrawal
        atm.withdraw();
        atm.dispenseCash();

        // Eject card
        atm.ejectCard();

        // Try operations without a card
        atm.withdraw();
        atm.enterPin();
    }
}
