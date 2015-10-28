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


public class ImageServer {
	public static ServerSocket ss_image = null;
	public static ServerSocket ss_chat = null;
	public static Socket con = null;
	public static ControlFrame frame;

    public static void main(String args[]) throws IOException{

		//create new sockets to accept connection
		try{
			ss_image = new ServerSocket(6000);
			ss_chat = new ServerSocket(6001);
		} catch(IOException e){}

		//create a image frame for this program
		frame = new ControlFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

        ImageThread imageThread = new ImageThread();
        imageThread.start();

        System.out.println(" created image thread");
        
        con = ss_chat.accept();
        System.out.println(con.getInetAddress());

		ChatThread chatThread = new ChatThread();
        chatThread.start();
        System.out.println(" created chat thread");
    }
}


/** 
    A frame with an image panel
*/
@SuppressWarnings("serial")
class ControlFrame extends JFrame{
    //use this to display the image from client
    public ImagePanel i_panel;

    /*----------------------- For chat related variables ------------------------------*/
    //use this to display the image from client
    public Panel c_panel;

    //these are the buttons for orient
    public JButton jb_up;
    public JButton jb_down;
    public JButton jb_left;
    public JButton jb_right;
    public static int clicked_bt = 0;

    // these button are used to change the icon of the android
    public JButton icon_1, icon_2, icon_3, icon_4, icon_5, icon_6, icon_7;

    //these are the buttons for message
    public JButton jb_1;

    //this is the textfield
    public JTextField input_field;
    public JTextField output_field;

    //for center
    public JLabel label2 = new JLabel();
    public static int send_flag = 0;
    public static int back_to_center = 0;
    public static String send_message = " ";
    public int bg_x = 153;
    int str_count = 0;
    int shift = -200;
    int scale = 17;

    // set the perference of the frame
    Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public int screenHeight = screenSize.height;
    public int screenWidth = screenSize.width;
    
    public int imageWidth = screenWidth/2;
    public int imageHeight = screenHeight*2/3;
    public int image_position = screenHeight/2 - imageHeight/2;

    public int buttonHeight = (screenHeight/scale)*2-10;
    public int buttonWidth1 = (screenWidth/2)/4 -5;
    public int buttonWidth2 = (screenWidth/2)/2 -5;

    public ControlFrame(){
        /*---------------- For image transmition related code -------------------*/

        // center frame in screen
        setTitle("Control Window");
        setLocation(0, 0);
        setSize(screenWidth, screenHeight);
        this.getContentPane().setBackground( Color.WHITE );
        this.getContentPane().setLayout(null);

        // add image panel to frame
        i_panel = new ImagePanel();

        //set the size of image
        i_panel.setSize(screenWidth/2,screenHeight); //640 480
        i_panel.setLocation(0, 0);
        i_panel.setBackground( Color.WHITE );
        add(i_panel);

        /*------------------- For chat related code -----------------------*/
        
        // add chat panel to frame
        c_panel = new Panel();

        //set the size of image
        c_panel.setSize(screenWidth/2,screenHeight); //640 480
        c_panel.setLocation(screenWidth/2, 0);
        c_panel.setLayout(null);
        c_panel.setBackground( Color.WHITE );

        String ip = null;
        String address = null; 

        try{
            InetAddress addr = InetAddress.getLocalHost(); 
            ip = addr.getHostAddress().toString();
            address = addr.getHostName().toString();
        }catch(IOException e){

        }
        
        //This button guide the user to input the correct ip of computer to the cell phone.
        jb_1 = new JButton(ip);
        //positon x, position y, size width, size height
        jb_1.setBounds(30, 5, screenWidth/2 - 30, (screenHeight/scale)*1 );
        jb_1.setFont(new Font("SansSerif",Font.ITALIC ,28) );
        jb_1.setBackground( Color.WHITE );
        // set all the button not to be focused, thus, we can catch the keyboard event
        jb_1.setFocusable(false);

        jb_1.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt){
                
            }
        });
        c_panel.add(jb_1);
        
        // Create an image to load image to set button icon
        Image img;

		
/*------------------------------ direction button on keyboard in Morse code -----------------------------------*/

        // Add a JText to receive the input command
        final JTextField cmd_input = new JTextField("", 50);
        String old_string = "";

        addKeyListener(new KeyListener() {
            
            public void keyPressed(KeyEvent e) {
                char input = e.getKeyChar();
                System.out.println( " received : " + input);

                switch(input){
                    // right
                    case 'e':
                        System.out.println(" right ");
                        send_message = "4";
                        clicked_bt = 4;
                        send_flag = 1;
                        break;
                    // left
                    case 't':
                        System.out.println(" left ");
                        send_message = "3";
                        clicked_bt = 3;
                        send_flag = 1;
                        break;
                    // up
                    case 'i':
                        System.out.println(" up ");
                        send_message = "1";
                        clicked_bt = 1;
                        send_flag = 1;
                        break;
                    // down
                    case 'm':
                        System.out.println(" down ");
                        send_message = "2";
                        clicked_bt = 2;
                        send_flag = 1;
                        break;
					// normal
					case 'a':
                        System.out.println(" normal ");
                        send_message = "A";
                        send_flag = 1;
                        break;
					// blink
					case 'n':
                        System.out.println(" blink ");
                        send_message = "B";
                        send_flag = 1;
                        break;
					// angry
					case 'd':
                        System.out.println(" angry ");
                        send_message = "C";
                        send_flag = 1;
                        break;
					// blank
					case 'r':
                        System.out.println(" blank ");
                        send_message = "D";
                        send_flag = 1;
                        break;
					// cry
					case 'u':
                        System.out.println(" cry ");
                        send_message = "E";
                        send_flag = 1;
                        break;
					// shy
					case 'k':
                        System.out.println(" shy ");
                        send_message = "F";
                        send_flag = 1;
                        break;
					// sleep
					case 's':
                        System.out.println(" sleep ");
                        send_message = "G";
                        send_flag = 1;
                        break;
                    default:
                        break;
                }
            }

            public void keyReleased(KeyEvent e) { 
                //System.out.println("release"); 
            }

            public void keyTyped(KeyEvent e) { 
                //System.out.println("keytyped"); 
            }
        });	
/*------------------------------ direction button on keyboard in Morse code -----------------------------------*/


/*--------------------------- direction button on PC -------------------------------*/

        jb_up = new JButton("* *");
        jb_up.setIcon(new ImageIcon((new ImageIcon(
            "image/up.png").getImage()
            .getScaledInstance(buttonWidth2, buttonHeight, java.awt.Image.SCALE_SMOOTH))));

        //positon x, position y, size width, size height
        jb_up.setBounds(30,(screenHeight/scale)*10, screenWidth/2 - 30,(screenHeight/scale)*2);
        jb_up.setFont(new Font("SansSerif",Font.ITALIC ,48) );
        // set the button not to be focused, thus, we can catch the keyboard event
        jb_up.setFocusable(false);

        jb_up.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt){
            	System.out.println(" clicked up");
                send_message = "1";
                clicked_bt = 1;
                send_flag = 1;
            }
        });
        c_panel.add(jb_up);

        jb_down = new JButton("_ _");
        jb_down.setIcon(new ImageIcon((new ImageIcon(
            "image/down.png").getImage()
            .getScaledInstance(buttonWidth2, buttonHeight, java.awt.Image.SCALE_SMOOTH))));
        
        //positon x, position y, size width, size height
        jb_down.setBounds(30,(screenHeight/scale)*14, screenWidth/2 - 30,(screenHeight/scale)*2);
        jb_down.setFont(new Font("SansSerif",Font.ITALIC ,48) ) ; 
        jb_down.setFocusable(false);

        jb_down.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt){
                System.out.println(" clicked down");
                send_message = "2";
                clicked_bt = 2;
                send_flag = 1;
            }
        });
        c_panel.add(jb_down);

        jb_left = new JButton("_");
        jb_left.setIcon(new ImageIcon((new ImageIcon(
            "image/left.png").getImage()
            .getScaledInstance(buttonWidth1, buttonHeight, java.awt.Image.SCALE_SMOOTH))));
        //positon x, position y, size width, size height
        jb_left.setBounds(30,(screenHeight/scale)*12, (screenWidth/2)/2 - 15,(screenHeight/scale)*2);
        jb_left.setFont(new Font("SansSerif",Font.ITALIC ,48) ) ; 
        jb_left.setFocusable(false);

        jb_left.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt){
                System.out.println(" clicked left");
                send_message = "3";
                clicked_bt = 3;
                send_flag = 1;
            }
        });
        c_panel.add(jb_left);

        jb_right = new JButton("*");
        jb_right.setIcon(new ImageIcon((new ImageIcon(
            "image/right.png").getImage()
            .getScaledInstance(buttonWidth1, buttonHeight, java.awt.Image.SCALE_SMOOTH))));
        //positon x, position y, size width, size height
        jb_right.setBounds((screenWidth/2)/2 + 15,(screenHeight/scale)*12, (screenWidth/2)/2 - 15,(screenHeight/scale)*2);
        jb_right.setFont(new Font("SansSerif",Font.ITALIC ,48) ) ;
        jb_right.setFocusable(false);

        jb_right.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt){
                System.out.println(" clicked right");
                send_message = "4";
                clicked_bt = 4;
                send_flag = 1;
            }
        });
        c_panel.add(jb_right);

/*--------------------------- direction button on PC -------------------------------*/
		
		
/*------------------------------ Change the Icon face of the android ----------------------------*/

       
        int iconLength = 0;
        int iconHeight = (screenHeight/scale)*3 -10;
        int iconWidth = (screenWidth/2)/4 -10;
        
        if(iconHeight < iconWidth){
            iconLength = iconHeight;
        } else{
            iconLength = iconWidth;
        }

        //normal face
		icon_1 = new JButton("*-");
        icon_1.setIcon(new ImageIcon((new ImageIcon(
            "image/normal.png").getImage()
            .getScaledInstance(iconLength, iconLength, java.awt.Image.SCALE_SMOOTH))));

        //positon x, position y, size width, size height
        icon_1.setBounds(0*(screenWidth/2)/3 + 30,(screenHeight/scale)*1, ((screenWidth-30)/2)/3 - 5,(screenHeight/scale)*3);
        icon_1.setFont(new Font("SansSerif",Font.ITALIC ,48) ) ;
        icon_1.setFocusable(false);

        icon_1.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt){
                System.out.println(" clicked ");
                send_message = "A";
                send_flag = 1;
            }
        });
        c_panel.add(icon_1);

        // blink face       
        icon_2 = new JButton("-*");
        icon_2.setIcon(new ImageIcon((new ImageIcon(
            "image/blink.png").getImage()
            .getScaledInstance(iconLength, iconLength, java.awt.Image.SCALE_SMOOTH))));
        
        //positon x, position y, size width, size height
        icon_2.setBounds(1*(screenWidth/2)/3 + 30,(screenHeight/scale)*1, ((screenWidth-30)/2)/3 -5,(screenHeight/scale)*3);
        icon_2.setFont(new Font("SansSerif",Font.ITALIC ,48) ) ;
        icon_2.setFocusable(false);

        icon_2.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt){
                System.out.println(" clicked ");
                send_message = "B";
                send_flag = 1;
            }
        });
        c_panel.add(icon_2);

        // angry face       
        icon_3 = new JButton("-**");
        icon_3.setIcon(new ImageIcon((new ImageIcon(
            "image/angry.png").getImage()
            .getScaledInstance(iconLength, iconLength, java.awt.Image.SCALE_SMOOTH))));
        
        //positon x, position y, size width, size height
        icon_3.setBounds(2*(screenWidth/2)/3 + 30,(screenHeight/scale)*1, ((screenWidth-30)/2)/3 -5,(screenHeight/scale)*3);
        icon_3.setFont(new Font("SansSerif",Font.ITALIC ,48) ) ;
        icon_3.setFocusable(false);

        icon_3.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt){
                System.out.println(" clicked ");
                send_message = "C";
                send_flag = 1;
            }
        });
        c_panel.add(icon_3);

        // blank face      
        icon_4 = new JButton("*-*");
        icon_4.setIcon(new ImageIcon((new ImageIcon(
            "image/blank.png").getImage()
            .getScaledInstance(iconLength, iconLength, java.awt.Image.SCALE_SMOOTH))));
        
        //positon x, position y, size width, size height
        icon_4.setBounds(0*(screenWidth/2)/4 + 30,(screenHeight/scale)*4, ((screenWidth-30)/2)/3 -5,(screenHeight/scale)*3);
        icon_4.setFont(new Font("SansSerif",Font.ITALIC ,48) ) ;
        icon_4.setFocusable(false);

        icon_4.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt){
                System.out.println(" clicked ");
                send_message = "D";
                send_flag = 1;
            }
        });
        c_panel.add(icon_4);

        // cry face       
        icon_5 = new JButton("**-");
        icon_5.setIcon(new ImageIcon((new ImageIcon(
            "image/cry.png").getImage()
            .getScaledInstance(iconLength, iconLength, java.awt.Image.SCALE_SMOOTH))));
        
        //positon x, position y, size width, size height
        icon_5.setBounds(1*(screenWidth/2)/3 + 30,(screenHeight/scale)*4, ((screenWidth-30)/2)/3 - 5,(screenHeight/scale)*3);
        icon_5.setFont(new Font("SansSerif",Font.ITALIC ,48) ) ;
        icon_5.setFocusable(false);

        icon_5.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt){
                System.out.println(" clicked ");
                send_message = "E";
                send_flag = 1;
            }
        });
        c_panel.add(icon_5);

        // shy face      
        icon_6 = new JButton("-*-");
        icon_6.setIcon(new ImageIcon((new ImageIcon(
            "image/shy.png").getImage()
            .getScaledInstance(iconLength, iconLength, java.awt.Image.SCALE_SMOOTH))));
        
        //positon x, position y, size width, size height
        icon_6.setBounds(2*(screenWidth/2)/3 + 30,(screenHeight/scale)*4, ((screenWidth-30)/2)/3 -5,(screenHeight/scale)*3);
        icon_6.setFont(new Font("SansSerif",Font.ITALIC ,48) ) ;
        icon_6.setFocusable(false);

        icon_6.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt){
                System.out.println(" clicked ");
                send_message = "F";
                send_flag = 1;
            }
        });
        c_panel.add(icon_6);

        // sleep face     
        icon_7 = new JButton("***");
        icon_7.setIcon(new ImageIcon((new ImageIcon(
            "image/sleep.png").getImage()
            .getScaledInstance(iconLength, iconLength, java.awt.Image.SCALE_SMOOTH))));
        
        //positon x, position y, size width, size height
        icon_7.setBounds(0*(screenWidth/2)/3 + 30,(screenHeight/scale)*7, ((screenWidth-30)/2)/3 -5,(screenHeight/scale)*3);
        icon_7.setFont(new Font("SansSerif",Font.ITALIC ,48) ) ;
        icon_7.setFocusable(false);

        icon_7.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt){
                System.out.println(" clicked ");
                send_message = "G";
                send_flag = 1;
            }
        });
        c_panel.add(icon_7);
		
/*------------------------------ Change the Icon face of the android ----------------------------*/

        add(c_panel);
        setFocusable(true);
    }

}

/**
    A panel that displays a tiled image
*/
@SuppressWarnings("serial")
class ImagePanel extends JPanel {     
	/*---------------------- For image transmition related code -------------------*/    
    private Image image;
    private InputStream input_stream;
    static int ImageCount = 0;
    static long current_time = 0;
    static long last_time = 0;
    static double fps = 0;
    
    public void getimage() throws IOException{
        Socket s = ImageServer.ss_image.accept();
        if(ImageCount % 30 == 0){
            current_time = System.currentTimeMillis();
            if((current_time - last_time) != 0){
                fps = 30/((double)(current_time - last_time)/1000);
            }
            System.out.println("Got image! fps : " + fps);
            last_time = current_time;
        }
        this.input_stream = s.getInputStream();
        this.image = ImageIO.read(input_stream);
        this.input_stream.close();

        ImageCount++;
    }
   
    public void paintComponent(Graphics g){  
        super.paintComponent(g);    
        if (image == null) return;

        Graphics2D g2 = (Graphics2D) g;

        //g2.rotate(Math.toRadians(270), ImageServer.frame.imageHeight, ImageServer.frame.imageWidth);  //640 480
        g2.drawImage( image, 0/*UP*/, ImageServer.frame.image_position/*RIGHT*/, ImageServer.frame.imageWidth, ImageServer.frame.imageHeight, null);
        //g2.drawImage( image, 1280-800/*UP*/, 533-800/*RIGHT*/, null);


        //g2.rotate(Math.toRadians(270), ImageServer.frame.image_height_position, ImageServer.frame.image_width_position);  //640 480
        //g2.drawImage( image, 0/*UP*/, 0/*RIGHT*/, null);
        //System.out.println("screen : " + ImageServer.frame.screenHeight + "  " + ImageServer.frame.screenWidth);
        //System.out.println("image : " + ImageServer.frame.imageHeight + "  " + ImageServer.frame.imageWidth);

    }
}