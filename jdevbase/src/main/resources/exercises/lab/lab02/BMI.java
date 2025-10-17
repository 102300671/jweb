import java.util.Scanner;
import java.lang.Math;

public class BMI {
  private double height;
  private double weight;
  private double bmi;
  private String result;
  public BMI(double height, double weight) {
    this.height = height;
    this.weight = weight;
  }
  public void Judgment() {
    bmi = weight / Math.pow(height, 2);
    if(bmi < 18.5) result = "Underweight";
    else if(bmi < 24) result = "Normal weight";
    else if(bmi < 27) result = "Overweight";
    else result = "Obese";
  }
  public void print() {
    System.out.printf("Your BMI is %.1f\n", bmi);
    System.out.printf("You are %s.\n", result);
  }
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    double height;
    double weight;
    System.out.print("Enter your height(m): ");
    height = sc.nextDouble();
    System.out.print("Enter your weight(kg): ");
    weight = sc.nextDouble();
    BMI bmi = new BMI(height, weight);
    bmi.Judgment();
    bmi.print();
    sc.close();
  }
}