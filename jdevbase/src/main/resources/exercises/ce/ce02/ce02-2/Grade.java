package cn.edu.ncist;

import java.util.Scanner;
public class Grade {
	public static void  main(String[] args) {
		Scanner sc=new Scanner(System.in);
		int score;
		score=sc.nextInt();
		if(score>=90) {
			System.out.println("excellent");
		} else if(score>=75) {
			System.out.println("good");
		} else if(score>=60) {
			System.out.println("pass"); 
		} else {
			System.out.println("fail");
		}
		sc.close();
	}
}