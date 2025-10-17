import java.util.Scanner;

public class Main{
    public static void main(String [] args){
        String name = "Tom";
        int age = 23;
        Scanner sc = new Scanner(System.in);
        double grade = sc.nextDouble();
        String result=judge(grade);
        
        System.out.println("name:" + name);
        System.out.println("age:" + age);
        System.out.println("grade:" + grade);
        System.out.println("result:" + result);
    }
    public static String judge(double grade) {
      if(grade >= 60) return "pass";
      else return "fail"; 
    }
}