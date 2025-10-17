public class NumberInverse {

	public static void main(String[] args) {

		int x = 369;

                int y; //逆转后的数

		//这里写逆转的代码
		y=0;
		while(x>0) {
		  y=y*10+x%10;
		  x=x/10;
		}

		System.out.println(y);

	}

}
