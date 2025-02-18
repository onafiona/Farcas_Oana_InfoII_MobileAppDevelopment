import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String text=sc.nextLine();
        for(int i=0;i<text.length();i++){
            char c = text.charAt(i);
            if(Character.isLowerCase(c))
                System.out.print(c);
        }
        for(int i=0;i<text.length();i++){
            char c = text.charAt(i);
            if(Character.isUpperCase(c))
                System.out.print(c);
        }
    }
}