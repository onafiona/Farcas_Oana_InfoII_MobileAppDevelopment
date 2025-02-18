import java.util.Scanner;

public class Main {
    public static int ValleyCounter(String input) {
        int seaLevel=0;
        int cnt=0;
        int above=0;
        int below=0;
        for(int i=0;i<input.length();i++){
            if(input.charAt(i)=='U'){
                seaLevel++;
            }
            else if(input.charAt(i)=='D'){
                seaLevel--;
            }
            if(seaLevel>=0){
                above=1;
            }
            if(above==1 && seaLevel<0){
                below=1;
            }
            if(below==1 && seaLevel>=0){
                cnt++;
                below=0;
                above=0;
            }
        }
        return cnt;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        System.out.println(ValleyCounter(input));
    }
}