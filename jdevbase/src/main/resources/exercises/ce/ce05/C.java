import java.util.Scanner;
public class Combination {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        //注意：下面代码多次调用factorial()方法
        long combi = factorial(n) / factorial(m) / factorial(n - m);

        System.out.println(combi);
    }
    //注意：下面代码仅计算参数n的阶乘
    public static long factorial(int n) {//n!
      long c = 1;
      for(int i = 2; i <= n; i++) {
        c *= i;
      }
      return c;
    }
}