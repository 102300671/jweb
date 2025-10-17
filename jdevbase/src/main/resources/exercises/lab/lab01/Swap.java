import java.util.Scanner;
public class Swap {
  public static void main(String[] args) {
    Scanner sc=new Scanner(System.in);
    int a,b,t;
    a=sc.nextInt();
    b=sc.nextInt();
    t=a;
    a=b;
    b=t;
    System.out.println(a+" "+b);
    sc.close();
  }
}