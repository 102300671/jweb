import java.util.Scanner;

public class Main{
    public static void main(String [] args){
        String name = "Tom";
        int age = 23;
        Scanner sc = new Scanner(System.in);
        double grade = sc.nextDouble();
        String r=judge(grade);
        
        print(name,age,grade,r);
    }
    public static void print(String name, int age, double grade, String r) {
      System.out.printf("name:%s\nage:%d\ngrade:%s\nresult:%s\n", name, age, grade, r);
    }
    public static String judge(double grade) {
        if(grade < 60 ){
            return "fail";
        } else {
            return "pass";
        }
    }
}
