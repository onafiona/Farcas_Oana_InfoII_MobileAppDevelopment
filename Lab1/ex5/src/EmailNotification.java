public class EmailNotification extends Notification implements Notifiable{

    public EmailNotification(String msg) {
        super(msg);
    }

    @Override
    public void sendNotification() {
        System.out.println("Email: " +getMessage());
    }
}
