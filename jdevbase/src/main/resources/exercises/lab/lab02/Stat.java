import java.util.Scanner;

public class Stat {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int i;
    int o = 0;
    int e = 0;
    while(true) {
      i = sc.nextInt();
      if(i == -1) break;
      else if(i %2 == 0) e++;
      else o++;
    }
    System.out.println(o+" "+e);
    sc.close();
  }
}