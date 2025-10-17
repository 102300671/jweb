public class ArrayDemo {

    public static void main(String[] args) {
        int arr[]= {34,56,8,9,90,0,23,45,78,123,3,98,66,76,55,12};
        //调用maxValue方法得到最大值并赋值给max变量
        int max = maxValue(arr);

        System.out.println(max);
    }
    //下面方法的功能是从arr数组中找出最大值并返回
    public static int maxValue(int[] arr) {
        int max = arr[0];
        for(int i =1; i < arr.length; i++) {
          if(max < arr[i]) {
            max = arr[i];
          }
        }
        return max;
    }

}