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
        OutputStream out = ImageServer.con.getOutputStream();
        Image img;
        while(true){
            try{
                Thread.sleep(10);
            } catch(InterruptedException ex){}

            if (ImageServer.frame.send_flag == 1) {
                out.write(ImageServer.frame.send_message.getBytes());
                out.flush();
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
