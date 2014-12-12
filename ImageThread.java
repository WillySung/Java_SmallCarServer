import java.lang.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.awt.event.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.imageio.*;
import javax.swing.*;
import java.net.Socket;
import java.net.ServerSocket;

class ImageThread extends Thread{

    //public static ServerSocket ss = null;

    public void run(){

    //get the next jpeg img from client
    while(true){
        try{
            ImageServer.frame.i_panel.getimage();
        } catch(IOException e){
            System.out.println("ERR!");
        }
        //due to our frame need to be repaint, it will call the conponentpaint function
        ImageServer.frame.repaint();
    }
  }
}
