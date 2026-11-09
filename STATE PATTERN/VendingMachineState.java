public class Main{
    public static void main(String[] args){
        VendingMachine machine = new VendingMachine();
        machine.insertCoin();
        machine.selectProduct();
        machine.dispense();
        machine.selectProduct();
        machine.dispense();
        machine.insertCoin();
        machine.dispense();
        machine.selectProduct();
        machine.insertCoin();
        machine.selectProduct();
        machine.dispense();
    }
}
interface vendingMachineState{
    void insertCoin(VendingMachine machine);
    void selectProduct(VendingMachine machine);
    void dispense(VendingMachine machine);
}
class IdleState implements vendingMachineState{
    public void insertCoin(VendingMachine machine){
        System.out.println("User Inserting coin...");
        machine.setState(new HasMoneyState());
    } 
    public void selectProduct(VendingMachine machine){
        System.out.println("User is yet to insert coins");
    }
    public void dispense(VendingMachine machine){
        System.out.println("Please insert coins and select product to dispense");
    }
}
class HasMoneyState implements vendingMachineState{
    public void insertCoin(VendingMachine machine){
        System.out.println("Machine already has coin");
    } 
    public void selectProduct(VendingMachine machine){
        System.out.println("User Selecting the product...");
        machine.setState(new ProductSelectedState());
    }
    public void dispense(VendingMachine machine){
        System.out.println("Please select the Product");
    }
}
class ProductSelectedState implements vendingMachineState{
    public void insertCoin(VendingMachine machine){
        System.out.println("Machine already has coin..");
    } 
    public void selectProduct(VendingMachine machine){
        System.out.println("User already selected the product");
    }
    public void dispense(VendingMachine machine){
        System.out.println("Dispensing the product..");
        machine.setState(new DispensingState());
    }
}
class DispensingState implements vendingMachineState{
    public void insertCoin(VendingMachine machine){
        System.out.println("Machine already has coin..");
    } 
    public void selectProduct(VendingMachine machine){
        System.out.println("User already selected the product");
    }
    public void dispense(VendingMachine machine){
        System.out.println("Dispensing the product..");
        machine.setState(new IdleState());
    }
}
//context class
class VendingMachine{
    private vendingMachineState state;
    public VendingMachine(){
        this.state = new IdleState();
    }
    public void setState(vendingMachineState state){
        this.state = state;
    }
    public void insertCoin(){
        state.insertCoin(this);
    }
    public void selectProduct(){
        state.selectProduct(this);
    }
    public void dispense(){
        state.dispense(this);
    }
}
