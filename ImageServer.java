import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.awt.event.*;
import java.lang.*;

import javax.imageio.*;
import javax.swing.*;
import java.net.*;


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

    //theses are the buttons for message
    public JButton jb_1;

    //this is the textfield
    public JTextField input_field;
    public JTextField output_field;

    //for ceter
    public JLabel label2 = new JLabel();
    public static int send_flag = 0;
    public static String send_message = "";
    public int bg_x = 153;
    int str_count = 0;
    int shift = -200;
    int scale = 11;

    // set the perference of the frame
    Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public int screenHeight = screenSize.height;
    public int screenWidth = screenSize.width;

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

        jb_1.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt){
                
            }
        });
        c_panel.add(jb_1);
        
        // Create an image to load image to set button icon
        Image img;

        jb_up = new JButton();
        try {
            img = ImageIO.read(getClass().getResource("image/up.png"));
            jb_up.setIcon(new ImageIcon(img));
            } catch (IOException ex) {
        }
        //positon x, position y, size width, size height
        jb_up.setBounds(30,(screenHeight/scale)*4, screenWidth/2 - 30,(screenHeight/scale)*2);
        jb_up.setFont(new Font("SansSerif",Font.ITALIC ,28) ) ; 

        jb_up.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt){
            	System.out.println(" clicked ");
                send_message = "1";
                clicked_bt = 1;
                send_flag = 1;
            }
        });
        c_panel.add(jb_up);

        jb_down = new JButton();
        try {
            img = ImageIO.read(getClass().getResource("image/down.png"));
            jb_down.setIcon(new ImageIcon(img));
            } catch (IOException ex) {
        }
        //positon x, position y, size width, size height
        jb_down.setBounds(30,(screenHeight/scale)*8, screenWidth/2 - 30,(screenHeight/scale)*2);
        jb_down.setFont(new Font("SansSerif",Font.ITALIC ,28) ) ; 

        jb_down.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt){
                System.out.println(" clicked ");
                send_message = "2";
                clicked_bt = 2;
                send_flag = 1;
            }
        });
        c_panel.add(jb_down);

        jb_left = new JButton();
        try {
            img = ImageIO.read(getClass().getResource("image/left.png"));
            jb_left.setIcon(new ImageIcon(img));
            } catch (IOException ex) {
        }
        //positon x, position y, size width, size height
        jb_left.setBounds(30,(screenHeight/scale)*6, (screenWidth/2)/2 - 10,(screenHeight/scale)*2);
        jb_left.setFont(new Font("SansSerif",Font.ITALIC ,28) ) ; 

        jb_left.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt){
                System.out.println(" clicked ");
                send_message = "3";
                clicked_bt = 3;
                send_flag = 1;
            }
        });
        c_panel.add(jb_left);

        jb_right = new JButton();
        try {
            img = ImageIO.read(getClass().getResource("image/right.png"));
            jb_right.setIcon(new ImageIcon(img));
            } catch (IOException ex) {
        }
        //positon x, position y, size width, size height
        jb_right.setBounds((screenWidth/2)/2+30,(screenHeight/scale)*6, (screenWidth/2)/2 -30,(screenHeight/scale)*2);
        jb_right.setFont(new Font("SansSerif",Font.ITALIC ,28) ) ; 

        jb_right.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt){
                System.out.println(" clicked ");
                send_message = "4";
                clicked_bt = 4;
                send_flag = 1;
            }
        });
        c_panel.add(jb_right);

        add(c_panel);
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
        g2.rotate(Math.toRadians(270),300,100);
        g2.drawImage(image, 0, 0, null);
    }
}