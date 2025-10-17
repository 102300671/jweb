import java.util.Scanner;

public class PrimeChecker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int number = sc.nextInt();
        if (isPrime(number)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
        sc.close();
    }

    public static boolean isPrime(int num) {
        if (num <= 1 || (num % 2 == 0 && num != 2)) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}