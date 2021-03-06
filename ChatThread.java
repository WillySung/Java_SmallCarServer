import java.lang.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.awt.event.*;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import java.net.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetSocketAddress;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;

import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Dimension;

class ChatThread extends Thread{

    //public Socket con = null;
    public int command_counter = 0;
    public int duration = 1000;  // moving for 1 sec per command

    public void run(){

    try{
        OutputStream output_stream = ImageServer.chat.getOutputStream();
        while(true){
            try{
                Thread.sleep(10);
            } catch(InterruptedException ex){}

            if (ImageServer.frame.send_flag == 1) {
                output_stream.write(ImageServer.frame.send_message.getBytes());
                output_stream.flush();
                command_counter ++;
                System.out.println("sending" + command_counter);     
                ImageServer.frame.send_flag = 0;
                
		/*----------------- clicked direction buttons turn into yellow ---------------------*/
		
                if (ImageServer.frame.clicked_bt == 1) {
                    //img = ImageIO.read(getClass().getResource("image/up_clicked.png"));
                    ImageServer.frame.jb_up.setIcon(new ImageIcon((new ImageIcon(
                        "image/up_clicked.png").getImage()
                        .getScaledInstance(ImageServer.frame.buttonWidth2, ImageServer.frame.buttonHeight, java.awt.Image.SCALE_SMOOTH))));
                }
                else if (ImageServer.frame.clicked_bt == 2) {
                    ImageServer.frame.jb_down.setIcon(new ImageIcon((new ImageIcon(
                        "image/down_clicked.png").getImage()
                        .getScaledInstance(ImageServer.frame.buttonWidth2, ImageServer.frame.buttonHeight, java.awt.Image.SCALE_SMOOTH))));
                }
                else if (ImageServer.frame.clicked_bt == 3) {
                    ImageServer.frame.jb_left.setIcon(new ImageIcon((new ImageIcon(
                        "image/left_clicked.png").getImage()
                        .getScaledInstance(ImageServer.frame.buttonWidth1, ImageServer.frame.buttonHeight, java.awt.Image.SCALE_SMOOTH))));
                }
                else if (ImageServer.frame.clicked_bt == 4) {
                    ImageServer.frame.jb_right.setIcon(new ImageIcon((new ImageIcon(
                        "image/right_clicked.png").getImage()
                        .getScaledInstance(ImageServer.frame.buttonWidth1, ImageServer.frame.buttonHeight, java.awt.Image.SCALE_SMOOTH))));
                }
				else if (ImageServer.frame.clicked_bt == 5) {
                    ImageServer.frame.icon_1.setBackground(Color.YELLOW);
                }
				else if (ImageServer.frame.clicked_bt == 6) {
                    ImageServer.frame.icon_2.setBackground(Color.YELLOW);
                }
				else if (ImageServer.frame.clicked_bt == 7) {
                    ImageServer.frame.icon_3.setBackground(Color.YELLOW);
                }
				else if (ImageServer.frame.clicked_bt == 8) {
                    ImageServer.frame.icon_4.setBackground(Color.YELLOW);
                }
				else if (ImageServer.frame.clicked_bt == 9) {
                    ImageServer.frame.icon_5.setBackground(Color.YELLOW);
                }
				else if (ImageServer.frame.clicked_bt == 10) {
                    ImageServer.frame.icon_6.setBackground(Color.YELLOW);
                }
				else if (ImageServer.frame.clicked_bt == 11) {
                    ImageServer.frame.icon_7.setBackground(Color.YELLOW);
                }
				else if (ImageServer.frame.clicked_bt == 12) {
                    ImageServer.frame.icon_8.setBackground(Color.YELLOW);
                }
				
                else{

                }
        /*----------------- clicked direction buttons turn into yellow ---------------------*/
		
		/*------------------ one second later, the yellow button turns back to red -----------------------*/
                try {
                    Thread.sleep(1000);
                    ImageServer.frame.jb_up.setIcon(new ImageIcon((new ImageIcon(
                        "image/up.png").getImage()
                        .getScaledInstance(ImageServer.frame.buttonWidth2, ImageServer.frame.buttonHeight, java.awt.Image.SCALE_SMOOTH))));
                    ImageServer.frame.jb_down.setIcon(new ImageIcon((new ImageIcon(
                        "image/down.png").getImage()
                        .getScaledInstance(ImageServer.frame.buttonWidth2, ImageServer.frame.buttonHeight, java.awt.Image.SCALE_SMOOTH))));
                    ImageServer.frame.jb_left.setIcon(new ImageIcon((new ImageIcon(
                        "image/left.png").getImage()
                        .getScaledInstance(ImageServer.frame.buttonWidth1, ImageServer.frame.buttonHeight, java.awt.Image.SCALE_SMOOTH))));
                    ImageServer.frame.jb_right.setIcon(new ImageIcon((new ImageIcon(
                        "image/right.png").getImage()
                        .getScaledInstance(ImageServer.frame.buttonWidth1, ImageServer.frame.buttonHeight, java.awt.Image.SCALE_SMOOTH))));
                    ImageServer.frame.icon_1.setBackground(Color.WHITE);
					ImageServer.frame.icon_2.setBackground(Color.WHITE);
					ImageServer.frame.icon_3.setBackground(Color.WHITE);
					ImageServer.frame.icon_4.setBackground(Color.WHITE);
					ImageServer.frame.icon_5.setBackground(Color.WHITE);
					ImageServer.frame.icon_6.setBackground(Color.WHITE);
					ImageServer.frame.icon_7.setBackground(Color.WHITE);
					ImageServer.frame.icon_8.setBackground(Color.WHITE);
                    ImageServer.frame.clicked_bt = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }			
		/*------------------ one second later, the yellow button turns back to red -----------------------*/
		
            }
        }
    }catch(IOException e){
        System.out.println("chat connect err");
    }

  }
}
