package CN3;
public class CalculatePostfixExpression {
	
	NodeStack<Integer> numberStack;//保存操作数的栈
	public CalculatePostfixExpression(){
		numberStack=new NodeStack<Integer>();
	}
	
	/**
	 * @param left：左元
	 * @param right：右元
	 * @param ch：运算符
	 * @return 两元运算的结果
	 */
	public int operating(int left,int right,char ch){
		if (ch=='+') return left+right;
		if (ch=='-') return left-right;
		if (ch=='*') return left*right;
		if (ch=='/') return left/right;
		return 0;
	}
	
	/**
	 * 
	 * @param postfixString:后缀表达式的字符串
	 * @return：后缀表达式的计算结果
	 */
	public int calculate(String postfixString){
		int i=0;
		int right,left;
		char ch;
		while (i<postfixString.length()){
			ch=postfixString.charAt(i);
			//当前字符是运算符，连续出栈两元，先出栈的元素在右，后出栈的元素在左，计算出结果后再入栈
			if (ch=='+' || ch=='-' || ch=='*' ||ch=='/') {
				right=numberStack.pop();
				left=numberStack.pop();
				numberStack.push(operating(left,right,ch));//连同操作符进行计算
				i++;
			}
			//当前字符是数字，则将其后面出现的数字字符都转化为数值，再入栈
			else if (ch>='0' && ch<='9'){
				int temp=0;
				do{
					temp=temp*10+ch-48;
					i++;
					
				}while (i<postfixString.length() &&(ch=postfixString.charAt(i))>='0' &&(ch=postfixString.charAt(i))<='9');//若下一个仍然是数字字符
				numberStack.push(temp);
			}
			
			//当前字符是空格，则下移一位
			else if (ch==' ') i++;
		}
		//最终留下的即为最终结果
		return numberStack.pop();
		
	}

}
