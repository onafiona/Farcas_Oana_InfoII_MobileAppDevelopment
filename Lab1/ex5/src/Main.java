public class Main {
    public static void main(String[] args) {
        Notifiable sms = new SMSNotification("Hello sent by SMS!");
        Notifiable email = new EmailNotification("Hello sent by Email!");
        Notifiable push = new PushNotification("Hello sent by Push Notification!");

        sms.sendNotification();
        email.sendNotification();
        push.sendNotification();
    }
}