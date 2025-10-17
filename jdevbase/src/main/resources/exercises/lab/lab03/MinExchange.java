import java.util.Scanner;

public class MinExchange {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int n = 6;
    int[] arr = new int[n];
    for(int i = 0; i < n; i++) {
      arr[i] = sc.nextInt();
    }
    int minIndex = 0;
    for(int i = 1; i < n; i++) {
      if(arr[minIndex] > arr[i]) {
        minIndex = i;
      }
    }
    int temp = arr[n - 1];
    arr[n - 1] = arr[minIndex];
    arr[minIndex] = temp;
    for(int i = 0; i < n; i++) {
      System.out.println(arr[i]);
    }
    sc.close();
  }
}