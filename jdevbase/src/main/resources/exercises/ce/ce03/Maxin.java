import java.util.Scanner;

public class Maxin {
  public static void main(String[] args) {
    Scanner sc=new Scanner(System.in);
    int n;
    n = sc.nextInt();
    int[] arr = new int[n];
    for(int i = 0; i < n; i++) {
      arr[i] = sc.nextInt();
    }
    int min;
    int max;
    min = max = arr[0];
    for(int i = 1; i < n; i++) {
      if(min > arr[i]) {
        min = arr[i];
      }
      if(max < arr[i]) {
        max = arr[i];
      }
    }
    System.out.println(max+" "+min);
    sc.close();
  }
}