package CN3;
public class InfixToPostfix {
	protected NodeStack<Character> operatorStack;//�����������ջ
	protected String postfixString;
	
	
	//���췽��
	public InfixToPostfix(){
		operatorStack=new NodeStack<Character>();
		postfixString=new String();
	}
	
	/**
	 * �Ƚ���������������ȼ�
	 * @param char pre:ջ���������
	 * @param char now:��ʱ�������
	 * @return int:pre��now���ȼ��ߣ�ͬһ��������ʽ����ߵ����ȣ�,����1��pre��now���ȼ���,����-1��
	 */
	public int compareOperator(char pre,char now){
		if (pre=='('|| pre=='#') return -1;
		if (now=='+' || now=='-') return 1;
		if (pre=='*' || pre=='/') return 1;
		return -1;
	}

	
	
	/**
	 * ����׺���ʽת��Ϊ��׺���ʽ
	 * @param infixString
	 */
	public void toPostfix(String infixString){
		int i;
		char ch;//ÿ��ȡ�����ַ�
		
		/*��chɨ�����ʽ��һ�����������Ҫ������ջ��������������ǰ���������Ƚϣ�����ǰ��û����������������ٵ�ջ��һ����ջ��ֻ����'('��ջ����������������compare���Ż��������
		 * ���ǿ����Ƚ�#��Ϊջ��Ԫ����ջ������compare������һ���ж���������ǰһ���ַ���'('��'#'ʱ...��������һ���������compare(pre,now)�����󣬲��ᷢ������,��ؽ����-1������ջ����
		 */
		operatorStack.push('#');
		for (i=0;i<infixString.length();i++){
			ch=infixString.charAt(i);
			
			//ch�������
			if (ch=='+' || ch=='-' || ch=='*' ||ch=='/'){
				//ջ������������ȼ���ch�ͣ�����ֱ����ջ
				if (compareOperator(operatorStack.top(),ch)<0) {
					operatorStack.push(ch);
					postfixString+=' ';//�ں�׺���ʽ֮β��һ���ո�����������ֵĻ�������3+75��37+5
				}
				
				
				else{
					//����ch���ȼ��ߵ������������������ʽĩβ
					while (compareOperator(operatorStack.top(),ch)>0){
						postfixString+=operatorStack.pop();
						postfixString+=' ';//�ں�׺���ʽ֮β��һ���ո�����������ֵĻ�������3+75��37+5
					}
					operatorStack.push(ch);
				}
			}
			
			//ch�ǲ���������ֱ�ӽ����������׺���ʽ֮β
			else if (ch>='0' && ch<='9')  postfixString+=ch;
			
			//ch��'(',��ֱ����ջ
			else if (ch=='(') operatorStack.push(ch);
			
			//ch��')',��'('ǰ���ַ����ŵ���׺���ʽ֮β��ֱ��ջ��Ϊ'(',��ȥ()
			else if (ch==')'){
				while (operatorStack.top()!='(')
					postfixString+=operatorStack.pop();
				operatorStack.pop();
			}
		}
		while(operatorStack.top()!='#') postfixString+=operatorStack.pop();//������ջ�е�Ԫ�ص������뵽��׺���ʽĩβ
		operatorStack.pop();//��'#'����
	}
	
	/**
	 * ���غ�׺���ʽ
	 * @return������ʽ
	 */
	public String getpostfixString(){
		return postfixString;
	}
	
}
