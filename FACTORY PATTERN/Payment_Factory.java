
interface Payment{
    void pay(double amount);
}
class UPIPayment implements Payment{
    public void pay(double amount){
        System.out.println("Processing the UPI Payment of Rs."+amount);
    }
}
class CardPayment implements Payment{
    public void pay(double amount){
        System.out.println("Processing the Card Payment of Rs."+amount);
    }
}
class DebitPayment implements Payment{
    public void pay(double amount){
        System.out.println("Processing the Debit Payment of Rs."+amount);
    }
}
class PaymentFactory{
    public static Payment createPayment(String type){ //return the common interface rather than a concrete implementation
        if(type.equalsIgnoreCase("UPI")){
            return new UPIPayment();
        }
        if(type.equalsIgnoreCase("CARD")){
            return new CardPayment();
        }
        if(type.equalsIgnoreCase("DEBIT")){
            return new DebitPayment();
        }
        throw new IllegalArgumentException (
            "Illegal Payment method: " + type
        );
    }
}
public class Main{
    public static void main(String[] args){
        Payment P1 = PaymentFactory.createPayment("UPI");
        P1.pay(1000.0);
        Payment P2 = PaymentFactory.createPayment("CARD");
        P2.pay(29078.0);
        Payment P3 = PaymentFactory.createPayment("DEBIT");
        P3.pay(1209000.0);
        Payment P4 = PaymentFactory.createPayment("CASH");
        P4.pay(1000.0);
    }
}

