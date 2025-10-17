import java.util.Scanner;

public class AboveAvg {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    double[] grade = new double[n];
    double sum = 0;
    double avg;
    int nompeo = 0;
    for(int i = 0; i < n; i++) {
      grade[i] = sc.nextDouble();
      sum += grade[i];
    }
    avg = sum / n;
    for(int i = 0; i < n; i++) {
      if(grade[i] > avg) {
        nompeo++;
      }
    }
    System.out.println(nompeo);
    sc.close();
  }
}