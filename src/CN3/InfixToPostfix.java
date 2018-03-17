package CN3;
public class InfixToPostfix {
	protected NodeStack<Character> operatorStack;//保存运算符的栈
	protected String postfixString;
	
	
	//构造方法
	public InfixToPostfix(){
		operatorStack=new NodeStack<Character>();
		postfixString=new String();
	}
	
	/**
	 * 比较两个运算符的优先级
	 * @param char pre:栈顶的运算符
	 * @param char now:此时的运算符
	 * @return int:pre比now优先级高（同一级别，则表达式中左边的优先）,返回1；pre比now优先级低,返回-1；
	 */
	public int compareOperator(char pre,char now){
		if (pre=='('|| pre=='#') return -1;
		if (now=='+' || now=='-') return 1;
		if (pre=='*' || pre=='/') return 1;
		return -1;
	}

	
	
	/**
	 * 将中缀表达式转化为后缀表达式
	 * @param infixString
	 */
	public void toPostfix(String infixString){
		int i;
		char ch;//每次取出的字符
		
		/*当ch扫到表达式第一个运算符，需要将其入栈，但其面临与其前面的运算符比较，而其前面没有运算符，即它面临的栈是一个空栈或只存有'('的栈，若不做处理，调用compare发放会出现意外
		 * 我们考虑先将#作为栈底元素入栈，并在compare中增加一个判断条件：当前一个字符是'('或'#'时...这样当第一个运算符做compare(pre,now)操作后，不会发生意外,令返回结果是-1，做入栈操作
		 */
		operatorStack.push('#');
		for (i=0;i<infixString.length();i++){
			ch=infixString.charAt(i);
			
			//ch是运算符
			if (ch=='+' || ch=='-' || ch=='*' ||ch=='/'){
				//栈顶运算符的优先级比ch低，则将其直接入栈
				if (compareOperator(operatorStack.top(),ch)<0) {
					operatorStack.push(ch);
					postfixString+=' ';//在后缀表达式之尾加一个空格，以免造成数字的混淆，如3+75和37+5
				}
				
				
				else{
					//将比ch优先级高的运算符依次输出到表达式末尾
					while (compareOperator(operatorStack.top(),ch)>0){
						postfixString+=operatorStack.pop();
						postfixString+=' ';//在后缀表达式之尾加一个空格，以免造成数字的混淆，如3+75和37+5
					}
					operatorStack.push(ch);
				}
			}
			
			//ch是操作数，则直接将它输出到后缀表达式之尾
			else if (ch>='0' && ch<='9')  postfixString+=ch;
			
			//ch是'(',则直接入栈
			else if (ch=='(') operatorStack.push(ch);
			
			//ch是')',则将'('前的字符都放到后缀表达式之尾，直至栈顶为'(',消去()
			else if (ch==')'){
				while (operatorStack.top()!='(')
					postfixString+=operatorStack.pop();
				operatorStack.pop();
			}
		}
		while(operatorStack.top()!='#') postfixString+=operatorStack.pop();//将存在栈中的元素弹并加入到后缀表达式末尾
		operatorStack.pop();//将'#'弹出
	}
	
	/**
	 * 返回后缀表达式
	 * @return后序表达式
	 */
	public String getpostfixString(){
		return postfixString;
	}
	
}
