import java.util.Scanner;

public class Friend {
  public String color;
  public String food;
  public String animal;
  public String name;
  
  public Friend(String color, String food, String animal, String name) {
    this.color = color;
    this.food = food;
    this.animal = animal;
    this.name = name;
  }
  public void print() {
    System.out.printf("I had a dream that %s ate a %s %s and said that it tasted like %s!\n", name, color, animal, food);
  }
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Please input your favorite color, food, animal and the best friend line by line:");
    String color = sc.nextLine();
    String food = sc.nextLine();
    String animal = sc.nextLine();
    String name = sc.nextLine();
    Friend sentence = new Friend(color, food, animal, name);
    sentence.print();
    sc.close();
  }
}