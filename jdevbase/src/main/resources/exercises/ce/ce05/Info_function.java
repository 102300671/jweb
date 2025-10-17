import java.util.Scanner;

public class StudentInfo{
    public static void main(String [] args){
        String n = "Tom";
        int age = 23;
        Scanner sc = new Scanner(System.in);
        double chengji= sc.nextDouble();
        //call judge()
        
String result = judge(chengji);

        //call print()
                
print(n, age, chengji, result);

    }
    
    public static void print(String n,int age,double g,String result) {
        System.out.println("name:" + n);
        System.out.println("age:" + age);
        System.out.println("grade:" + g);
        System.out.println("result:" + result);
    }
    
    public static String judge(double grade) {
        if(grade < 60 ){
            return "fail";
        } else {
            return "pass";
        }
    }
}