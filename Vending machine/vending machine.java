
import java.util.*;

// 1. State interface
interface VendingMachineState {
    void selectProduct(VendingMachine machine, String productId);
    void insertMoney(VendingMachine machine, double amount);
    void dispense(VendingMachine machine);
    void cancel(VendingMachine machine);
}

// 2. Payment Strategy interface
interface PaymentStrategy {
    boolean pay(double amount);
}

// 3. Payment implementations
class CashPayment implements PaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.println("Cash payment accepted: Rs." + amount);
        return true;
    }
}

class UpiPayment implements PaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.println("UPI payment successful: Rs." + amount);
        return true;
    }
}

class CardPayment implements PaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.println("Card payment successful: Rs." + amount);
        return true;
    }
}

// 4. Product
class Product {
    private final String id;
    private final String name;
    private final double price;
    private int quantity;

    public Product(String id, String name, double price, int quantity) {
        if (price <= 0 || quantity < 0) {
            throw new IllegalArgumentException("Invalid product details");
        }

        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void reduceQuantity() {
        if (quantity <= 0) {
            throw new IllegalStateException("Product out of stock");
        }
        quantity--;
    }
}

// 5. Vending Machine
class VendingMachine {
    private final Map<String, Product> inventory = new HashMap<>();

    private VendingMachineState currentState;
    private Product selectedProduct;
    private double insertedMoney;

    public VendingMachine() {
        currentState = new IdleState();
    }

    public void addProduct(Product product) {
        inventory.put(product.getId(), product);
    }

    public Product getProduct(String productId) {
        return inventory.get(productId);
    }

    public VendingMachineState getState() {
        return currentState;
    }

    public void setState(VendingMachineState state) {
        currentState = state;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product product) {
        selectedProduct = product;
    }

    public double getInsertedMoney() {
        return insertedMoney;
    }

    public void addMoney(double amount) {
        insertedMoney += amount;
    }

    public void resetMoney() {
        insertedMoney = 0;
    }

    // Public operations delegate to the current state
    public void selectProduct(String productId) {
        currentState.selectProduct(this, productId);
    }

    public void insertMoney(double amount) {
        currentState.insertMoney(this, amount);
    }

    public void dispense() {
        currentState.dispense(this);
    }

    public void cancel() {
        currentState.cancel(this);
    }

    // Payment Strategy is selected by the caller
    public boolean makePayment(PaymentStrategy strategy, double amount) {
        return strategy.pay(amount);
    }
}

// 6. Idle state
class IdleState implements VendingMachineState {

    @Override
    public void selectProduct(VendingMachine machine, String productId) {
        Product product = machine.getProduct(productId);

        if (product == null) {
            System.out.println("Invalid product.");
            return;
        }

        if (product.getQuantity() <= 0) {
            System.out.println("Product out of stock.");
            return;
        }

        machine.setSelectedProduct(product);
        machine.setState(new HasMoneyState());

        System.out.println("Selected: " + product.getName()
                + ", Price: Rs." + product.getPrice());
    }

    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        System.out.println("Please select a product first.");
    }

    @Override
    public void dispense(VendingMachine machine) {
        System.out.println("Please select a product first.");
    }

    @Override
    public void cancel(VendingMachine machine) {
        System.out.println("Nothing to cancel.");
    }
}

// 7. Transaction state
class HasMoneyState implements VendingMachineState {

    @Override
    public void selectProduct(VendingMachine machine, String productId) {
        System.out.println("Complete or cancel the current transaction first.");
    }

    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }

        machine.addMoney(amount);

        System.out.println("Inserted: Rs." + amount);
        System.out.println("Total inserted: Rs."
                + machine.getInsertedMoney());
    }

    // Cash-based dispensing
    @Override
    public void dispense(VendingMachine machine) {
        Product product = machine.getSelectedProduct();

        if (product == null) {
            machine.setState(new IdleState());
            return;
        }

        if (product.getQuantity() <= 0) {
            System.out.println("Product out of stock.");
            cancel(machine);
            return;
        }

        double inserted = machine.getInsertedMoney();

        if (inserted < product.getPrice()) {
            System.out.println("Insufficient money. Please insert Rs."
                    + (product.getPrice() - inserted) + " more.");
            return;
        }

        // Cash is the payment method for this transaction
        boolean paid = machine.makePayment(
                new CashPayment(), product.getPrice());

        if (!paid) {
            System.out.println("Payment failed.");
            return;
        }

        product.reduceQuantity();

        double change = inserted - product.getPrice();

        System.out.println("Dispensing: " + product.getName());

        if (change > 0) {
            System.out.println("Please collect change: Rs." + change);
        }

        resetTransaction(machine);
    }

    @Override
    public void cancel(VendingMachine machine) {
        System.out.println("Transaction cancelled.");

        if (machine.getInsertedMoney() > 0) {
            System.out.println("Refunding: Rs."
                    + machine.getInsertedMoney());
        }

        resetTransaction(machine);
    }

    private void resetTransaction(VendingMachine machine) {
        machine.resetMoney();
        machine.setSelectedProduct(null);
        machine.setState(new IdleState());
    }
}

// 8. Main
public class Main {
    public static void main(String[] args) {
        VendingMachine machine = new VendingMachine();

        machine.addProduct(new Product("P1", "Coke", 40, 5));
        machine.addProduct(new Product("P2", "Chips", 25, 3));

        // Scenario 1: Cash purchase with insufficient money
        machine.selectProduct("P1");
        machine.insertMoney(20);
        machine.dispense();

        // Insert more money and complete the purchase
        machine.insertMoney(30);
        machine.dispense();

        // Scenario 2: Cancel and refund
        machine.selectProduct("P2");
        machine.insertMoney(50);
        machine.cancel();

        // Scenario 3: Digital payment using Strategy Pattern
        Product chips = machine.getProduct("P2");

        if (chips != null && chips.getQuantity() > 0) {
            PaymentStrategy payment = new UpiPayment();

            if (machine.makePayment(payment, chips.getPrice())) {
                chips.reduceQuantity();
                System.out.println("Dispensing: " + chips.getName());
            }
        }
    }
}
