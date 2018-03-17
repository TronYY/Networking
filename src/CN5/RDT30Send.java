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
     /* 输入要发送的数据 */  
     input = JOptionPane.showInputDialog("input the sending data!");
     /* 为发送的数据包注册相应的时钟监听器 */ 
     Timer aTimer = new Timer(5000 , new SenderListener(input , counter%2));
     /* 启动时钟监听器 */
     aTimer.start();
    
     do
     {
      /* 接收回应方的相关信息 */
 configure = answerToSender();
 while(true)
 {
    /* 如果数据包已经成功接收,则终止相应的时钟监听器
    /* 否则数据包重发,时钟监听器继续监听 */
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
/*　实现了ActionListener监听器接口的自定义类,在该类中必须实现
　　ActionListener接口中的actionPerformed(ActionEvent)方法,该
　　方法在时间间隔到达你所定义的时间间隔时会被自动调用　*/
class SenderListener implements ActionListener
{
   private String series;
   private int sequenceNumber;
   /* 构造器,在对象初始化时被调用 */ 
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