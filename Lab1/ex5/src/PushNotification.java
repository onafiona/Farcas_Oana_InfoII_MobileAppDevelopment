public class PushNotification extends Notification implements Notifiable {
    public PushNotification(String msg) {
        super(msg);
    }

    @Override
    public void sendNotification() {
        System.out.println("Push: " +getMessage());
    }
}
