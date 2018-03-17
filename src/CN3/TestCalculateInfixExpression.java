package CN3;

import java.util.Scanner;
public class TestCalculateInfixExpression {

	public static void main(String[] args) {
		Scanner input=new Scanner(System.in);
		String s;
		System.out.println("请输入中缀表达式：");
		s=input.nextLine();
		InfixToPostfix ITP=new InfixToPostfix();
		ITP.toPostfix(s);
		System.out.println("后缀表达式为："+ITP.getpostfixString());
		CalculatePostfixExpression CPE=new CalculatePostfixExpression();
		System.out.println("计算结果为："+CPE.calculate(ITP.getpostfixString()));
	}

}
