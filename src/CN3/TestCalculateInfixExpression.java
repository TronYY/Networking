package CN3;

import java.util.Scanner;
public class TestCalculateInfixExpression {

	public static void main(String[] args) {
		Scanner input=new Scanner(System.in);
		String s;
		System.out.println("��������׺���ʽ��");
		s=input.nextLine();
		InfixToPostfix ITP=new InfixToPostfix();
		ITP.toPostfix(s);
		System.out.println("��׺���ʽΪ��"+ITP.getpostfixString());
		CalculatePostfixExpression CPE=new CalculatePostfixExpression();
		System.out.println("������Ϊ��"+CPE.calculate(ITP.getpostfixString()));
	}

}
