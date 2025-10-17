import java.util.Scanner;

public class PerNum {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int n, m, sum;
    n = sc.nextInt();
    m = sc.nextInt();
    for(int i = n; i <= m; i++) {
      if(i < 6) continue;
      sum = 1;
      for(int j = 2; j <= (int)Math.sqrt(i); j++) {
        if(i % j == 0) {
          sum += j;
          if(j != i / j) sum += i / j;
        }
      }
      if(sum == i) System.out.print(i+" ");
    }
    System.out.println();
    sc.close();
  }
}