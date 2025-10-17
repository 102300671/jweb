import java.util.Scanner;

public class Main {
        
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double[] grade = new double[6];
        for(int i=0;i<grade.length;i++) {
            grade[i] = sc.nextDouble();
        }
        //调用求平均值的方法
        double aver = aver(grade);

        System.out.println("average="+aver);
        //调用求最大值的方法
        System.out.println("maxGrade="+max(grade));
        //调用排序的方法
        sort(grade);

                //输出排序后的结果
        for(int i=0;i<grade.length;i++) {
            System.out.print(grade[i]+" ");
        }
    }

        //定义求平均值的方法，该方法返回平均值
    public static double aver(double[] grade) {
      double sum = 0;
      for(int i = 0; i < grade.length; i++) {
        sum += grade[i];
      }
      return sum / grade.length;
    }

    
    //定义求最大成绩的方法，方法返回最大值
    public static double max(double[] grade) {
      double max = grade[0];
      for(int i =1; i < grade.length; i++) {
          if(max < grade[i]) {
            max = grade[i];
          }
        }
        return max;
    }


    //定义由大到小排序的方法，方法返回值为void
    public static void sort(double[] grade) {
      double temp;
      int maxIndex;
      for(int i = 0; i < grade.length; i++) {
        maxIndex = i;
        for(int j = i + 1; j < grade.length; j++) {
          if(grade[maxIndex] < grade[j]) {
            maxIndex = j;
          }
        }
        if(i != maxIndex) {
          temp = grade[i];
          grade[i] = grade[maxIndex];
          grade[maxIndex] = temp;
        }
      }
    }

}