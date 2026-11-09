interface Notification{
    void send(String message);
}
class EmailNotification implements Notification{
    public void send(String message){
        System.out.println("Sending Email: "+message);
    }
}
class SMSNotification implements Notification{
    public void send(String message){
        System.out.println("Sending SMS: "+message);
    }
}
class PushNotification implements Notification{
    public void send(String message){
        System.out.println("Push Notification: "+message);
    }
}
class NotificationFactory{
    public static Notification createNotification(String type){
        if(type.equalsIgnoreCase("EMAIL")){
            return new EmailNotification();
        }
        if(type.equalsIgnoreCase("SMS")){
            return new SMSNotification();
        }
        if(type.equalsIgnoreCase("PUSH")){
            return new PushNotification();
        }
        //throw new when creating and throwing an exception.
        throw new IllegalArgumentException(
            "Invalid notification type: " + type
        );
    }
}

public class Main{
    public static void main(String[] args){
        Notification n1 = NotificationFactory.createNotification("EMAIL");
        n1.send("You order has been shipped");
        Notification n2 = NotificationFactory.createNotification("SMS");
        n2.send("Your otp is 1224435");
        Notification n3 = NotificationFactory.createNotification("PUSH");
        n3.send("You have new notification");
        
    }
}