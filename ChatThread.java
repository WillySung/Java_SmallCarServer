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

    public void run(){

    try{
        OutputStream out = ImageServer.con.getOutputStream();

        while(true){
            if (ImageServer.frame.send_flag == 1) {
                out.write(ImageServer.frame.send_message.getBytes());
                out.flush();
                command_counter ++;
                System.out.println("sending" + command_counter);
                ImageServer.frame.send_flag = 0;
            }
            if (ImageServer.frame.back_to_center == 1) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ImageServer.frame.back_to_center = 0;
            }
            try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }catch(IOException e){
        System.out.println("chat connect err");
    }

  }
}
