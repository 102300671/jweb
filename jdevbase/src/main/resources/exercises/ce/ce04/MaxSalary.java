import java.util.Scanner;

public class MaxSalary {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int[] salary = new int[6];
    salary[0] = sc.nextInt();
    int max = salary[0];
    int index = 0;
    for(int i = 1; i < 6; i++) {
      salary[i] = sc.nextInt();
      if(max < salary[i]) {
        max = salary[i];
        index = i;
      }
    }
    System.out.println("No " + (index + 1) + " salary maxsalary:" + max);
    sc.close();
  }
}