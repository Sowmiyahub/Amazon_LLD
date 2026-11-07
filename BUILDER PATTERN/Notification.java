
//Builder constructs the Notification. A separate Strategy or Factory can decide how to deliver it.
import java.time.LocalDateTime;

enum Channel {
    EMAIL, SMS, PUSH
}

enum NotificationPriority {
    LOW, NORMAL, HIGH
}

class Notification {

    // 1. Fields
    private final String notificationId;
    private final String recipient;
    private final String message;
    private final Channel channel;

    // Optional fields
    private final NotificationPriority priority;
    private final String title;
    private final LocalDateTime scheduledTime;

    // 2. Private constructor
    private Notification(Builder builder) {
        this.notificationId = builder.notificationId;
        this.recipient = builder.recipient;
        this.message = builder.message;
        this.channel = builder.channel;
        this.priority = builder.priority;
        this.title = builder.title;
        this.scheduledTime = builder.scheduledTime;
    }

    // 3. Getters
    public String getNotificationId() { return notificationId; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public Channel getChannel() { return channel; }
    public NotificationPriority getPriority() { return priority; }
    public String getTitle() { return title; }
    public LocalDateTime getScheduledTime() { return scheduledTime; }

    // 4. Static nested Builder
    public static class Builder {

        // Mandatory fields
        private final String notificationId;
        private final String recipient;
        private final String message;
        private final Channel channel;

        // Optional fields
        private NotificationPriority priority;
        private String title;
        private LocalDateTime scheduledTime;

        // 5. Builder constructor
        public Builder(String notificationId, String recipient,
                       String message, Channel channel) {
            this.notificationId = notificationId;
            this.recipient = recipient;
            this.message = message;
            this.channel = channel;
        }

        // 6. Optional field methods
        public Builder priority(NotificationPriority priority) {
            this.priority = priority;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder scheduledTime(LocalDateTime scheduledTime) {
            this.scheduledTime = scheduledTime;
            return this;
        }

        // 7. Build method
        public Notification build() {

            if (notificationId == null || notificationId.isBlank()) {
                throw new IllegalArgumentException("Notification ID required");
            }

            if (recipient == null || recipient.isBlank()) {
                throw new IllegalArgumentException("Recipient required");
            }

            if (message == null || message.isBlank()) {
                throw new IllegalArgumentException("Message required");
            }

            if (channel == null) {
                throw new IllegalArgumentException("Channel required");
            }

            return new Notification(this);
        }
    }
}

public class Main {
    public static void main(String[] args) {

        Notification notification =
            new Notification.Builder(
                "N101",
                "sowmi@gmail.com",
                "Your order has been shipped!",
                Channel.EMAIL
            )
            .title("Order Update")
            .priority(NotificationPriority.HIGH)
            .scheduledTime(LocalDateTime.now())
            .build();

        System.out.println("Notification ID: " + notification.getNotificationId());
        System.out.println("Recipient: " + notification.getRecipient());
        System.out.println("Message: " + notification.getMessage());
        System.out.println("Channel: " + notification.getChannel());
        System.out.println("Priority: " + notification.getPriority());
        System.out.println("Title: " + notification.getTitle());
    }
}
