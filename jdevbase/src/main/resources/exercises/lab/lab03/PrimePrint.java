import java.util.Scanner;

public class PrimePrint {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    int count = 0;
    for(int i = 101; i < n; i+=2) {
      boolean isprime = true;
      for(int j = 2; j <= Math.sqrt(i); j++) {
        if(i % j == 0) {
          isprime = false;
          break;
        }
      }
      if(isprime) {
        System.out.print(i + " ");
        count++;
        if(count % 5 == 0) {
        System.out.println("");
        }
      }
    }
    if(count % 5 != 0) {
      System.out.println("");
    }
    sc.close();
  }
}