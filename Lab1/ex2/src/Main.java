import java.util.Scanner;

public class Main {
    public static void friendNum(int a, int b){
        int sumaA=1;
        int sumaB=1;
        for(int i=2;i<=a/2;i++)
        {
            if(a%i==0)
                sumaA+=i;
        }
        for(int i=2;i<=b/2;i++)
        {
            if(b%i==0)
                sumaB+=i;
        }
        if(sumaA==b && sumaB==a)
            System.out.println("True");
        else System.out.println("False");
        System.out.println("Suma primul numar: "+sumaA);
        System.out.println("Suma al doilea numar: "+sumaB);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a=sc.nextInt();
        int b=sc.nextInt();
        friendNum(a,b);
    }
}