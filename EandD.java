/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Nishmith Sai Reddy
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import static java.awt.Cursor.getPredefinedCursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EandD extends JFrame implements ActionListener
{
    String path,graypath,erodepath,dilatepath,bwpath,openpath,closepath;
    JPanel contentPane,contentPane2;
    JButton browseBtn,reset,grayBtn,bwBtn,erodeBtn,dilateBtn,openBtn,closeBtn,refBtn;
    JLabel imgLabel,imgLabel2;
    JLabel img1,img2,size,col,mes,head,msg;
    JTextField jsel;
    JComboBox jc;
    JLabel[] jl=new JLabel[10];
    Font myFont = new Font("Calibri", Font.BOLD, 24);
    Font myFont1 = new Font("Calibri", Font.BOLD, 20);
    Font myFont2 = new Font("Calibri", Font.ITALIC, 18);
    Font myFont3 = new Font("Calibri", Font.BOLD, 18);
    Font myFont4 = new Font("Calibri", Font.BOLD, 16);
    BufferedImage imageInput,imgInput;
    BufferedImage image;
    BufferedImage grayImage = null;
    BufferedImage binaryImage = null;
    BufferedImage erodedImage = null;
    BufferedImage dilatedImage=null;
    int height;
    int width;
    int gr=0,bw=0,se,oc;
    int fileToSave;
    public EandD()
    {
        setLayout(new BorderLayout());
        this.setTitle("Erosion and Dilation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        //setBounds(0, 0, 1920, 1080);
        
        contentPane=new JPanel();
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.setLayout(null);
        add(contentPane);
        
        head=new JLabel("MORPHOLOGICAL IMAGE PROCESSING");
        head.setBounds(800,50,420,50);
        head.setFont(myFont);
        head.setForeground(Color.WHITE);
        contentPane.add(head);
        
       for(int i=0;i<10;i++)
       {
            jl[i]=new JLabel();
            jl[i].setForeground(Color.WHITE);
       }
        jl[0].setFont(myFont1);
        jl[1].setFont(myFont1);
        for(int i=2;i<10;i++)
            jl[i].setFont(myFont2);
        jl[0].setText("MORPHOLOGICAL IMAGE PROCESSING");
        jl[1].setText("MORPHOLOGICAL OPERATIONS");
        jl[2].setText("Morphology is a broad set of image processing operations that process images based on shapes.");
        jl[3].setText("Morphological Image Processing is used in the place of a Linear Image Processing");
        jl[4].setText("Because it sometimes distorts the underlying geometric form of an image");
        jl[5].setText("The Morphological image processing operations are applied on binary images in forensics.");
        jl[6].setText("Erosion causes objects to shrink.");
        jl[7].setText("Dilation causes objects to dilate or grow in size.");
        jl[8].setText("The opening of an image is defined as Erosion followed by a Dilation.");
        jl[9].setText("The closing of an image is defined as a dilation followed by an erosion.");
        jl[0].setBounds(450,200,500,50);
        jl[2].setBounds(500,250,800,50);
        jl[3].setBounds(500,300,800,50);
        jl[4].setBounds(500,350,800,50);
        jl[5].setBounds(500,400,800,50);
        jl[1].setBounds(450,450,500,50);
        jl[6].setBounds(500,500,800,50);
        jl[7].setBounds(500,550,800,50);
        jl[8].setBounds(500,600,800,50);
        jl[9].setBounds(500,650,800,50);
        for(int i=0;i<10;i++)
            contentPane.add(jl[i]);
                    
        imgLabel=new JLabel();
        imgLabel.setBounds(200, 250, 665, 500);
        contentPane.add(imgLabel);
        
        imgLabel2=new JLabel();
        imgLabel2.setBounds(1000, 250, 665, 500);
        contentPane.add(imgLabel2);
        imgLabel2.setEnabled(true);
        imgLabel2.setVisible(true);
        
        browseBtn=new JButton("SELECT IMAGE");
        browseBtn.setBounds(900, 800, 120, 30);
        browseBtn.setBackground(Color.WHITE);
        browseBtn.setForeground(Color.BLACK);
        browseBtn.setFocusPainted(false);
        browseBtn.setVisible(true);
        contentPane.add(browseBtn);
        browseBtn.addActionListener(this);
        browseBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                
        reset=new JButton("RESET");
        reset.setBounds(900, 850, 120, 30);
        reset.setBackground(Color.WHITE);
        reset.setForeground(Color.BLACK);
        reset.setFocusPainted(false);
        reset.setVisible(false);
        contentPane.add(reset);
        reset.addActionListener(this);
        reset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        refBtn=new JButton("CLEAR");
        refBtn.setForeground(Color.BLACK);
        refBtn.setBackground(Color.WHITE);
        refBtn.setFocusPainted(false);
        refBtn.setBounds(1500, 50, 120, 30);
        refBtn.addActionListener(this);
        contentPane.add(refBtn);
        refBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        refBtn.setVisible(false);
        
        contentPane2=new JPanel();  
        contentPane2.setBackground(Color.LIGHT_GRAY);
        contentPane2.setVisible(false);
        contentPane2.setLayout(new GridLayout(1,2));
        contentPane2.setPreferredSize(new Dimension(50,50));
        contentPane2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(contentPane2,BorderLayout.PAGE_END);
        
        grayBtn=new JButton("GRAY SCALE");
        grayBtn.setForeground(Color.WHITE);
        grayBtn.setBackground(Color.BLACK);
        grayBtn.setFocusPainted(false);
        grayBtn.addActionListener(this);
        contentPane2.add(grayBtn);
        grayBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        bwBtn=new JButton("BINARY");
        bwBtn.setForeground(Color.WHITE);
        bwBtn.setBackground(Color.BLACK);
        bwBtn.setFocusPainted(false);
        bwBtn.addActionListener(this);
        contentPane2.add(bwBtn);
        bwBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        size=new JLabel("STRUCTURING ELEMENT SIZE:");
        size.setForeground(Color.BLACK);
        contentPane2.add(size);
        
        jsel=new JTextField(5);
        jsel.setForeground(Color.WHITE);
        jsel.setBackground(Color.BLACK);
        contentPane2.add(jsel);
        
        col=new JLabel("SELECT OBJECT COLOR:");
        col.setForeground(Color.BLACK);
        contentPane2.add(col);
        
        jc=new JComboBox();
        jc.addItem("black");
        jc.addItem("white");
        jc.setForeground(Color.WHITE);
        jc.setBackground(Color.BLACK);
        contentPane2.add(jc);
        jc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        erodeBtn=new JButton("EROSION");
        erodeBtn.setForeground(Color.WHITE);
        erodeBtn.setBackground(Color.BLACK);
        erodeBtn.setFocusPainted(false);
        erodeBtn.addActionListener(this);
        contentPane2.add(erodeBtn);
        erodeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        dilateBtn=new JButton("DILATION");
        dilateBtn.setForeground(Color.WHITE);
        dilateBtn.setBackground(Color.BLACK);
        dilateBtn.setFocusPainted(false);
        dilateBtn.addActionListener(this);
        contentPane2.add(dilateBtn);
        dilateBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        openBtn=new JButton("OPENING");
        openBtn.setForeground(Color.WHITE);
        openBtn.setBackground(Color.BLACK);
        openBtn.setFocusPainted(false);
        openBtn.addActionListener(this);
        contentPane2.add(openBtn);
        openBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
        
        closeBtn=new JButton("CLOSING");
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setBackground(Color.BLACK);
        closeBtn.setFocusPainted(false);
        closeBtn.addActionListener(this);
        contentPane2.add(closeBtn);
        closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
         
        
        img1=new JLabel();
        img1.setBounds(450,200,130,25);
        img1.setFont(myFont3);
        img1.setForeground(Color.WHITE);
        contentPane.add(img1);
                
        img2=new JLabel();
        img2.setBounds(1250,200,150,25);     
        img2.setFont(myFont3);
        img2.setForeground(Color.WHITE);
        contentPane.add(img2);
        
        mes=new JLabel();
        mes.setBounds(1066,800, 500, 200);
        mes.setForeground(Color.WHITE);
        mes.setFont(myFont4); 
        contentPane.add(mes);
        
        msg = new JLabel();
        msg.setForeground(Color.WHITE);
        msg.setVisible(false);
        msg.setBounds(1066, 800, 500, 200);
        contentPane.add(msg);
   }

    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
        if(s.equals("SELECT IMAGE"))
        {
            gr=0;
            bw=0;
            JFileChooser file = new JFileChooser();
            //file.setCurrentDirectory(new File(System.getProperty("user.home")));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
            file.addChoosableFileFilter(filter);
            int result = file.showSaveDialog(null);
            if(result == JFileChooser.APPROVE_OPTION)
            {
                File selectedFile = file.getSelectedFile();
                path = selectedFile.getAbsolutePath();   
                imgLabel.setEnabled(true);
                imgLabel.setVisible(true);
                imgLabel.setIcon(ResizeImage(path));
                img2.setText("Resultant Image...");
                imgLabel2.setEnabled(false);
                imgLabel2.setVisible(false);
                contentPane2.setVisible(true);
                img1.setText("Original Image");
                reset.setVisible(true);
                refBtn.setVisible(true);
                jsel.setText("");
                for(int i=0;i<10;i++)
                    jl[i].setVisible(false);
                msg.setVisible(false);
            }
            else if(result == JFileChooser.CANCEL_OPTION)
            {
                System.out.println("No File Select");
                msg.setText("*No image Selected*");
                msg.setVisible(true);
            }   
        }
        if(s.equals("RESET"))
        {
            imgLabel.setEnabled(false);
            imgLabel.setVisible(false);
            imgLabel2.setEnabled(false);
            imgLabel2.setVisible(false);
            img1.setText("");
            img2.setText("");
            jsel.setText("");
            contentPane2.setVisible(false);
            reset.setVisible(false);
            refBtn.setVisible(false);
            for(int i=0;i<10;i++)
                jl[i].setVisible(true);
        }
        if(s.equals("CLEAR"))
        {
            imgLabel.setEnabled(true);
            imgLabel.setVisible(true);
            jsel.setText("");
            imgLabel.setIcon(ResizeImage(path));
            img2.setText("Resultant Image...");
            contentPane2.setVisible(true);
            img1.setText("Original Image");
            imgLabel2.setVisible(false);
            
        }    
        if(s.equals("GRAY SCALE"))
        {
            if(gr==0)
            {
                toGray();
                gr++;
            }
            imgLabel.setIcon(ResizeImage(path));
            imgLabel2.setIcon(ResizeImage(graypath));
            imgLabel2.setEnabled(true);
            imgLabel2.setVisible(true);
            img1.setText("Original Image");
            img2.setText("GrayScale Image");
        }
        if(s.equals("BINARY"))
        {
            if(bw==0)
            {
                toBinary();
                bw++;
            }
            imgLabel.setIcon(ResizeImage(path));
            imgLabel2.setIcon(ResizeImage(bwpath));
            imgLabel2.setEnabled(true);
            imgLabel2.setVisible(true);
            img1.setText("Original Image");
            img2.setText("Binary Image");
        }
        if(s.equals("EROSION"))
        {
            int err=0;
            if(gr==0)
            {
                toGray();
                gr++;
            }
            if(bw==0)
            {
                toBinary();
                bw++;
            }
            try
            {
                if(jsel.getText().equals(""))
                {
                    err=1;
                    mes.setText("*please enter Structuring Element size");
                }
                se=Integer.parseInt(jsel.getText());
                oc=jc.getSelectedIndex();
                if(se<0)
                {
                    err=1;
                    mes.setText("*negative Structuring Element is not allowed");
                }
                if(se>21)
                {
                    err=1;
                    mes.setText("*Structuring element should not exceed 21");
                }    
                if(se%2==0 && se>0)
                {
                    err=1;
                    mes.setText("*Generally Structuring Element size is odd");
                }
                if(se>=0&&se<3)
                {
                    err=1;
                    mes.setText("*A Structuring Element size should be atleast 3");
                }
            }
            catch(Exception ee)
            {
                System.out.println(ee);
                err=1;
            }
            if(err==0)
            {
                toErode(binaryImage,se,oc);
                erodepath=path;
                erodepath=erodepath.replace(".jpg","_eroded.jpg");
                File output_file3=new File(erodepath);
                try 
                {
                    ImageIO.write(erodedImage, "jpg", output_file3);
                } 
                catch (IOException ex) 
                {
                    System.out.println(ex);
                }
                imgLabel.setIcon(ResizeImage(bwpath));
                imgLabel2.setIcon(ResizeImage(erodepath));
                imgLabel2.setEnabled(true);
                imgLabel2.setVisible(true);
                img1.setText("Binary Image");
                img2.setText("Eroded Image");
                mes.setText("");
            }
        }
        if(s.equals("DILATION"))
        {
            int err=0;
            if(gr==0)
            {
                toGray();
                gr++;
            }
            if(bw==0)
            {
                toBinary();
                bw++;
            }
            try
            {
                if(jsel.getText().equals(""))
                {
                    err=1;
                    mes.setText("*please enter SE size");
                }
                se=Integer.parseInt(jsel.getText());
                oc=jc.getSelectedIndex();
                if(se<0)
                {
                    err=1;
                    mes.setText("*negative SE is not allowed");
                }
                if(se%2==0 && se>0)
                {
                    err=1;
                    mes.setText("*Generally SE size is odd");
                }
                if(se>=0&&se<3)
                {
                    err=1;
                    mes.setText("*A SE size should be atleast 3");
                }
            }
            catch(Exception ee)
            {
                System.out.println(ee);
                err=1;
            }
            if(err==0)
            {
                toDilate(binaryImage,se,oc);
                dilatepath=path;
                dilatepath=dilatepath.replace(".jpg","_dilated.jpg");
                File output_file3=new File(dilatepath);
                try 
                {
                    ImageIO.write(dilatedImage, "jpg", output_file3);
                }
                catch (IOException ex)
                {
                    System.out.println(ex);
                }
                imgLabel.setIcon(ResizeImage(bwpath));
                imgLabel2.setIcon(ResizeImage(dilatepath));
                imgLabel2.setEnabled(true);
                imgLabel2.setVisible(true);
                img1.setText("Binary Image");
                img2.setText("Dilated Image");
                mes.setText("");
            }
        }
        if(s.equals("OPENING"))
        {
            int err=0;
            if(gr==0)
            {
                toGray();
                gr++;
            }
            if(bw==0)
            {
                toBinary();
                bw++;
            }
            try
            {
                if(jsel.getText().equals(""))
                {
                    err=1;
                    mes.setText("*please enter Structuring Element size");
                }
                se=Integer.parseInt(jsel.getText());
                oc=jc.getSelectedIndex();
                if(se<0)
                {
                    err=1;
                    mes.setText("*negative Strcturing Element is not allowed");
                }
                if(se%2==0 && se>0)
                {
                    err=1;
                    mes.setText("*Generally Structuring Element size is odd");
                }
                if(se>=0&&se<3)
                {
                    err=1;
                    mes.setText("*A Structuring Element size should be atleast 3");
                }
            }
            catch(Exception ee)
            {
                System.out.println(ee);
                err=1;
            }
            if(err==0)
            {
                toErode(binaryImage,se,oc);
                toDilate(erodedImage,se,oc);
                openpath=path;
                openpath=openpath.replace(".jpg","_opening.jpg");
                File output_file3=new File(openpath);
                try 
                {
                    ImageIO.write(dilatedImage, "jpg", output_file3);
                }
                catch (IOException ex)
                {
                    System.out.println(ex);
                }
                imgLabel.setIcon(ResizeImage(bwpath));
                imgLabel2.setIcon(ResizeImage(openpath));
                imgLabel2.setEnabled(true);
                imgLabel2.setVisible(true);
                img1.setText("Binary Image");
                img2.setText("Opening Image");
                mes.setText("");
            }
        }
        if(s.equals("CLOSING"))
        {
            int err=0;
            if(gr==0)
            {
                toGray();
                gr++;
            }
            if(bw==0)
            {
                toBinary();
                bw++;
            }
            try
            {
                if(jsel.getText().equals(""))
                {
                    err=1;
                    mes.setText("*please enter Structuring Element size");
                }
                se=Integer.parseInt(jsel.getText());
                oc=jc.getSelectedIndex();
                if(se<0)
                {
                    err=1;
                    mes.setText("*negative Structuring Element is not allowed");
                }
                if(se%2==0 && se>0)
                {
                    err=1;
                    mes.setText("*Generally Structuring Element size is odd");
                }
                if(se>=0 && se<3)
                {
                    err=1;
                    mes.setText("*A Structuring Element size should be atleast 3");
                }
            }
            catch(Exception ee)
            {
                System.out.println(ee);
                err=1;
            }
            if(err==0)
            {
                toDilate(binaryImage,se,oc);
                toErode(dilatedImage,se,oc);
                closepath=path;
                closepath=closepath.replace(".jpg","_closing.jpg");
                File output_file3=new File(closepath);
                try 
                {
                    ImageIO.write(erodedImage, "jpg", output_file3);
                } 
                catch (IOException ex) 
                {
                    System.out.println(ex);
                }
                imgLabel.setIcon(ResizeImage(bwpath));
                imgLabel2.setIcon(ResizeImage(closepath));
                imgLabel2.setEnabled(true);
                imgLabel2.setVisible(true);
                img1.setText("Binary Image");
                img2.setText("Closing Image");
                mes.setText("");
            }
        }
    }
    public ImageIcon ResizeImage(String ImagePath)
    {
        ImageIcon MyImage = new ImageIcon(ImagePath);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon retImage = new ImageIcon(newImg);
        return retImage;
    }
    public void toGray()
    {
        try
        {
            File input_file=new File(path);
            image=ImageIO.read(input_file);
            height=image.getHeight();
            width=image.getWidth();
            BufferedImage gray=new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
            WritableRaster gRaster=gray.getRaster();
            for(int i=0; i<height; i++) 
            {
                for(int j=0; j<width; j++) 
                {
                    Color c = new Color(image.getRGB(j, i));
                    int red = (int)(c.getRed() * 0.299);
                    int green = (int)(c.getGreen() * 0.587);
                    int blue = (int)(c.getBlue() * 0.114);
                    int n=red+green+blue;
                    gRaster.setSample(j, i, 0, n);
                }
            }
            graypath=path;
            graypath=graypath.replace(".jpg","_gray.jpg");
            File output_file=new File(graypath);
            ImageIO.write(gray, "jpg", output_file);
        }
        catch(Exception e)
        {
            System.out.println("Exception:"+e);
        }
    }
    public void toBinary()
    {
       try
       {
            File input_file=new File(path);
            image=ImageIO.read(input_file);
            height=image.getHeight();
            width=image.getWidth();
            binaryImage= new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_BYTE_BINARY);
            WritableRaster binRaster = binaryImage.getRaster();
            WritableRaster gRaster = image.getRaster();
            for(int h=0;h<height;h++)
            {
                for(int w=0;w<width;w++)
                {
                    int[]p = new int[3];
                    gRaster.getPixel(w,h,p);
                    if(p[0]>127)
                    {
                        binRaster.setSample(w,h,0,1);
                    }
                    else
                    {
                        binRaster.setSample(w,h,0,0);
                    }
                }
            }
            bwpath=path;
            bwpath=bwpath.replace(".jpg","_bw.jpg");
            File output_file2=new File(bwpath);
            ImageIO.write(binaryImage, "jpg", output_file2);
       }
       catch(Exception e)
       {
            System.out.println("Exception:"+e);
       }
    }
    public void  toErode(BufferedImage image,int StructuringElementSize,int erosionColor)
    {
        erodedImage = new BufferedImage(image.getWidth(),image.getHeight(),image.getType());
        WritableRaster binRaster = image.getRaster();
        WritableRaster eroRaster = erodedImage.getRaster();
        width = image.getWidth();
        height = image.getHeight();
        int s=StructuringElementSize;
        int ele=s/2;
        int i,j;
        int target=erosionColor;
        int reverse;
        if(target==1)
            reverse=0;
        else
            reverse=1; 
        for(int x=0;x<width;x++)
        {
            for(int y=0;y<height;y++)
            {
                i=0;
                j=0;
                int [][]mat=new int[s][s];
                for(int tx=x-ele;tx<=x+ele;tx++)
                {
                    j=0;
                    for(int ty=y-ele;ty<=y+ele;ty++)
                    {
                        if(ty>=0 && ty<height && tx>=0 && tx<width)
                        {
                            int q=0;
                            int eleVal=binRaster.getSample(tx, ty, q);
                            mat[i][j]=eleVal;
                            j++;
                        }
                    }
                    i++;
                }
                int count=0;
                for(int a=0;a<s;a++)
                    for(int b=0;b<s;b++)
                        if(mat[a][b]==target)
                            count++;
                if(count==s*s)
                    eroRaster.setSample(x,y,0,target);
                else
                    eroRaster.setSample(x,y,0,reverse);
            }
        }
   }
    public void  toDilate(BufferedImage image,int StructuringElementSize,int dilationColor)
    {
        dilatedImage = new BufferedImage(image.getWidth(),image.getHeight(),image.getType());
        WritableRaster binRaster = image.getRaster();
        WritableRaster dilRaster = dilatedImage.getRaster();
        width = image.getWidth();
        height = image.getHeight();
        int s=StructuringElementSize;
        int ele=s/2;
        int i,j;
        int target=dilationColor;
        int reverse;
        if(target==1)
            reverse=0;
        else
            reverse=1; 
        for(int x=0;x<width;x++)
        {
            for(int y=0;y<height;y++)
            {
                i=0;
                j=0;
                int [][]mat=new int[s][s];
                for(int tx=x-ele;tx<=x+ele;tx++)
                {
                    j=0;
                    for(int ty=y-ele;ty<=y+ele;ty++)
                    {
                        if(ty>=0 && ty<height && tx>=0 && tx<width)
                        {
                            int q=0;
                            int eleVal=binRaster.getSample(tx, ty, q);
                            mat[i][j]=eleVal;
                            j++;
                        }
                    }
                    i++;
                }
                int count=0;
                for(int a=0;a<s;a++)
                    for(int b=0;b<s;b++)
                        if(mat[a][b]==target)
                            count++;
                if(count>=1)
                    dilRaster.setSample(x,y,0,target);
                else
                    dilRaster.setSample(x,y,0,reverse);                
            }
        }
    }
    
   public static void main(String[] args)
   {
        EandD frame = new EandD();
        frame.setVisible(true);
   }
}