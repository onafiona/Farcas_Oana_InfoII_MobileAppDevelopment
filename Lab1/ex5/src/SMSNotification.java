public class SMSNotification extends Notification implements Notifiable{

    public SMSNotification(String msg) {
        super(msg);
    }

    @Override
    public void sendNotification() {
        System.out.println("SMS: " + getMessage());
    }
}
