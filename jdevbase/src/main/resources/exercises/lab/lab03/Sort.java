import java.util.Scanner;

public class Sort {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int n = 10;
    int[] arr = new int[n];
    int temp;
    int minIndex;
    for(int i = 0; i < n; i++) {
      arr[i] = sc.nextInt();
    }
    for(int i = 0; i < n; i++) {
      minIndex = i;
      for(int j = i + 1; j < n; j++) {
        if(arr[minIndex] > arr[j]) {
          minIndex = j;
        }
      }
      if(i != minIndex) {
        temp = arr[i];
        arr[i] = arr[minIndex];
        arr[minIndex] = temp;
      }
    }
    for(int i = 0; i < n; i++) {
      System.out.print(arr[i]);
      if(i != n - 1) {
        System.out.print(",");
      }
    }
    System.out.println("");
    sc.close();
  }
}