import java.util.Scanner;

public class MaxSalaryStr {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String[] salarystr = sc.nextLine().split(" ");
    int index = 0;
    int max = Integer.MIN_VALUE;
    for(int i = 0; i < salarystr.length; i++) {
      int salary = Integer.parseInt(salarystr[i]);
      if(max < salary) {
        max = salary;
        index = i;
      }
    }
    System.out.println("No " + (index + 1) + " salary maxsalary:" + max);
    sc.close();
  }
}