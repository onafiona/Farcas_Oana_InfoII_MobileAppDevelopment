public abstract class Notification {
    private String msg;
    public Notification(String message) {
        this.msg = message;
    }
    public String getMessage() {
        return msg;
    }
    public void setMessage(String message) {
        this.msg = message;
    }
    public void displayNotification() {
        System.out.println("Notification: " + msg);
    }
}
