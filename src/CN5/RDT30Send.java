import javax.swing.*;
import java.awt.event.*;
import javax.swing.Timer;
public class Sender
{
   public static void main(String [] args)
   {
     String input;
     int counter = 0;
     int configure;
     /* ����Ҫ���͵����� */  
     input = JOptionPane.showInputDialog("input the sending data!");
     /* Ϊ���͵����ݰ�ע����Ӧ��ʱ�Ӽ����� */ 
     Timer aTimer = new Timer(5000 , new SenderListener(input , counter%2));
     /* ����ʱ�Ӽ����� */
     aTimer.start();
    
     do
     {
      /* ���ջ�Ӧ���������Ϣ */
 configure = answerToSender();
 while(true)
 {
    /* ������ݰ��Ѿ��ɹ�����,����ֹ��Ӧ��ʱ�Ӽ�����
    /* �������ݰ��ط�,ʱ�Ӽ������������� */
    if(configure == counter%2)
    {
       aTimer.stop();
       break;
    }
          
    else configure = answerToSender();
 }
 input = JOptionPane.showInputDialog("continue?(Y/N)");
 if(input.equals("N") || input.equals("n")) break;




input = JOptionPane.showInputDialog("input the sending data!");
 aTimer = new Timer(5000 , new SenderListener(input , ++counter%2));
 aTimer.start();
     }while(true);
   
     System.exit(0);
  }
 
  public static int answerToSender()
  {
     String input = JOptionPane.showInputDialog("response to sender(0/1)");
     return Integer.parseInt(input);
  }
}
/*��ʵ����ActionListener�������ӿڵ��Զ�����,�ڸ����б���ʵ��
����ActionListener�ӿ��е�actionPerformed(ActionEvent)����,��
����������ʱ�����������������ʱ����ʱ�ᱻ�Զ����á�*/
class SenderListener implements ActionListener
{
   private String series;
   private int sequenceNumber;
   /* ������,�ڶ����ʼ��ʱ������ */ 
   public SenderListener(String series ,int counter)
   {
      this.series = series;
      sequenceNumber = counter;
      System.out.println("the datas: '" + series + "' have been sended!\n"
     + "          " + " and its sequence number is: " + sequenceNumber + "\n");
   }
 
   public void actionPerformed(ActionEvent aevent)
   {
      System.out.println("the datas: '" + series + "' recieved failurely!");