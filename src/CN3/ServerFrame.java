package CN3;


import java.awt.*; 

import java.net.*; 

import java.awt.event.*;
import java.net.DatagramPacket; 

import java.net.DatagramSocket; 

import java.net.SocketException; 

public class ServerFrame { 

 private Frame f; 

 private TextField tf; 

 private Button but;  



  private TextArea ta; 

 private DatagramSocket ds1; 

 

 ServerFrame() { 

  init(); 

 } 

 

 public TextArea getTextArea() { 

  return this.ta; 

 } 

 

 public TextField getTextField() { 

  return this.tf; 

 } 

 

 public void init() { 

  f = new Frame("服务端"); 

  f.setBounds(300, 100, 400, 300); 

  f.setLayout(new FlowLayout()); 

  tf = new TextField(40); 

 

  but = new Button("发送"); 

 

  ta = new TextArea(25, 50); 

  f.add(tf); 

  f.add(but); 

  f.add(ta); 

  myEvent(); 

  f.setVisible(true); 

 } 

 

 private void myEvent() { 

 

  f.addWindowListener(new WindowAdapter() { 

   public void windowClosing(WindowEvent e) { 

    System.exit(0); 

   } 

  }); 

 

  but.addActionListener(new ActionListener() { 

   public void actionPerformed(ActionEvent e) { 

    try { 

     ds1 = new DatagramSocket(); 

  



      byte[] buf1 = tf.getText().getBytes(); 

     DatagramPacket dp1 = new DatagramPacket(buf1, buf1.length, 

       InetAddress.getByName("127.0.0.1"), 10002); 

     ds1.send(dp1); 

     ta.append("服务端说::"+tf.getText()+"\r\n"); 

     tf.setText(""); 

 

    } catch (Exception ex) { 

     ex.toString(); 

    } 

 

   } 

  }); 

 } 

}
