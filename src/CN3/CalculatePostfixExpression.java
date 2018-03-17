package CN3;
public class CalculatePostfixExpression {
	
	NodeStack<Integer> numberStack;//�����������ջ
	public CalculatePostfixExpression(){
		numberStack=new NodeStack<Integer>();
	}
	
	/**
	 * @param left����Ԫ
	 * @param right����Ԫ
	 * @param ch�������
	 * @return ��Ԫ����Ľ��
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
	 * @param postfixString:��׺���ʽ���ַ���
	 * @return����׺���ʽ�ļ�����
	 */
	public int calculate(String postfixString){
		int i=0;
		int right,left;
		char ch;
		while (i<postfixString.length()){
			ch=postfixString.charAt(i);
			//��ǰ�ַ����������������ջ��Ԫ���ȳ�ջ��Ԫ�����ң����ջ��Ԫ�����󣬼�������������ջ
			if (ch=='+' || ch=='-' || ch=='*' ||ch=='/') {
				right=numberStack.pop();
				left=numberStack.pop();
				numberStack.push(operating(left,right,ch));//��ͬ���������м���
				i++;
			}
			//��ǰ�ַ������֣����������ֵ������ַ���ת��Ϊ��ֵ������ջ
			else if (ch>='0' && ch<='9'){
				int temp=0;
				do{
					temp=temp*10+ch-48;
					i++;
					
				}while (i<postfixString.length() &&(ch=postfixString.charAt(i))>='0' &&(ch=postfixString.charAt(i))<='9');//����һ����Ȼ�������ַ�
				numberStack.push(temp);
			}
			
			//��ǰ�ַ��ǿո�������һλ
			else if (ch==' ') i++;
		}
		//�������µļ�Ϊ���ս��
		return numberStack.pop();
		
	}

}
