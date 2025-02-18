import java.util.Scanner;

public class Main {

    public static int hexToDec(String hexa) {
        return Integer.parseInt(hexa, 16);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String hexa=sc.nextLine();
        System.out.println("numarul transformat: "+hexToDec(hexa));
    }
}