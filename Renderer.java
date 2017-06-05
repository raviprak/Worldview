package renderer;

import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import data.*;

public class Renderer extends JFrame implements ActionListener
{
	Container cp;
	JFileChooser fc;
	JTextField angle, distance;
	JLabel toshow;
	Spocen thethng;
	point p;
	double l, m, n, dist, ax, ay, az;
	long ldata;
	int range;
	
	public Renderer()
	{
		super("WorldView Renderer");
		fc=new JFileChooser(new File("back"));
		cp=getContentPane();
		setMembers();
		setupMenu();		
		setVisible(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		l=m=0.0;n=1.0;
		ax=ay=Math.PI/2; az=0;
	}
	
	void setMembers()
	{
		setLayout(new FlowLayout());
		toshow=new JLabel();
		cp.add(toshow);		
		JButton up=new JButton("Up", new ImageIcon("images/up.gif"));
		JButton down=new JButton("Down", new ImageIcon("images/down.gif"));
		JButton left=new JButton("Left", new ImageIcon("images/left.gif"));
		JButton right=new JButton("Right", new ImageIcon("images/right.gif"));
		JButton movein=new JButton("Move In", new ImageIcon("images/movein.gif"));
		JButton moveout=new JButton("Move Out", new ImageIcon("images/moveout.gif"));	
		angle=new JTextField("Angle",3);
		up.addActionListener(this);
		down.addActionListener(this);
		left.addActionListener(this);
		right.addActionListener(this);
		distance=new JTextField("Distance",4);
		movein.addActionListener(this);
		moveout.addActionListener(this);
		
		cp.add(angle);
		cp.add(up); cp.add(down); cp.add(left); cp.add(right); 
		cp.add(distance); cp.add(movein); cp.add(moveout);
		pack();		
	}
	
	void setupMenu()
	{
		JMenuBar bar=new JMenuBar();
		JMenu file=new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);		
		JMenuItem open=new JMenuItem("Open an object", new ImageIcon("images/open.gif"));
		open.setMnemonic(KeyEvent.VK_O);
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
		open.addActionListener(new eventexec(this));
		file.add(open);
		bar.add(file);				
		setJMenuBar(bar);
	}
	
	void opendialog()
	{
		int ret=fc.showOpenDialog(Renderer.this);
		if(ret==JFileChooser.APPROVE_OPTION)
		{
			File toopen=fc.getSelectedFile();
			thethng=new Spocen();
			thethng.readFromFile(toopen);
			p=thethng.getDimension();
			dist=Math.ceil(Math.sqrt(p.x*p.x+p.y*p.y+p.z*p.z));
			range=(int) dist;
			System.out.println(p.x+" "+p.y+" "+p.z+" "+range);
			showview();						
		}
		else return;
	}
	
	BufferedImage drawThing(BufferedImage view)
	{		
		Graphics pntr=view.getGraphics();		
		pntr.setColor(Color.WHITE);
		pntr.fillRect( 0, 0, view.getWidth(), view.getHeight() );
		ldata=0;
		int from=(int)(Math.ceil(dist)), to=-1*(int)(Math.ceil(dist));
		//if(ax>0&&ax<=90 && ay>0&&ay<=90 && az>0&&az<=90) {from=((int)Math.ceil(dist)); to=0;}
		//else if(ax>0&&ax<=90 && ay>0&&ay<=90 && az>90&&az<=180) {from=(int)Math.ceil(dist)); to=-1*(int)Math.ceil(dist));}
		for(int plx=-1*range; plx<range; plx++)
		{
			for(int ply=-1*range; ply<range; ply++)
			{
				for(int plz=-1*range; plz<range; plz++)
				{
					if((int)(l*plx+m*ply+n*plz)==0) //if plx,ply,plz is on the plane passing through origin
					{
						for(int r=from; r>=to; r--)
						{
							long data[]=thethng.getData((int) (r*l+plx), (int)(r*m+ply), (int)(r*n+plz));
							if(data[1]!=0)
							{
								//Write data at appropriate position																		
								double mult=(0.4*view.getWidth())/range;
								if(ldata!=data[1]) {pntr.setColor(translateColor(data[1], data[0])); ldata=data[1];}
								pntr.drawRect((int)(0.5*view.getWidth()+(plx*m+ plz*Math.sin(Math.acos(m)))*mult) , (int)(0.5*view.getWidth()+(ply*l- Math.sin(Math.acos(l)))*mult *(-1*plx*Math.sin(Math.acos(m))+plz*m)),(int) mult,(int) mult);
								//pntr.drawRect((int)(0.5*view.getWidth()+(n*plx-(Math.sin(Math.acos(n))*ply))*mult), (int)(((Math.sin(Math.acos(n))*plx+n*ply))*mult+0.5*view.getWidth()), (int) mult, (int) mult);
								//pntr.drawRect( (int)(0.5*view.getWidth()+(x+z*(Math.sin(Math.acos(m))+l))*mult), (int)((y+z*(Math.sin(Math.acos(l))+m))*mult+0.5*view.getWidth()), (int) mult, (int) mult);
								//pntr.drawRect( (int)(0.5*view.getWidth()+(x+z*(l/n))*mult), (int)((y+z*(m/n))*mult+0.5*view.getWidth()), (int) mult, (int) mult);
								/*double x1, y1;
								y1=(Math.sqrt(x*x + y*y + z*z)*y)/Math.sqrt(x*x+y*y);
								x1=y1*x/y;
								pntr.drawRect( (int)(0.5*view.getWidth()+(x1)*mult), (int)((y1)*mult+0.5*view.getWidth()), (int) mult, (int) mult);
								*/
								break;
							}
						}
					}
				}
			}
		}		
		return view;
	}
	
	void showview()
	{
		BufferedImage view;
		Dimension d=getSize();		
		//if(view==null)
		{
			if(d.width<=d.height) view=drawThing(new BufferedImage((int)(0.8*d.width), (int)(0.8*d.width), BufferedImage.TYPE_4BYTE_ABGR));
			else view=drawThing(new BufferedImage( (int)(0.8*d.height), (int) (0.8*d.height), BufferedImage.TYPE_4BYTE_ABGR));		
		}
		/*else 
		{
			if(d.width<=d.height) view=drawThing(view);
			else view=drawThing(view);							
		}*/
		
		ImageIcon temp=new ImageIcon(view);		
		toshow.setIcon(temp);
		
	}
	
	Color translateColor(long data, long nbits)
	{
		if(nbits%4!=0) return Color.BLACK;
		int r, g, b, a, filter=0;
		for(int i=0; i<nbits/4; i++)
		{
			filter=filter<<1; filter++;			
		}
		float max=filter;
		b=(int) data&filter;		
		filter=filter<<(nbits/4);	g=(int) data&filter;	g=g>>>(nbits/4);
		filter=filter<<(nbits/4);	r=(int) data&filter;	r=r>>>(nbits/2);
		filter=filter<<(nbits/4);	a=(int) data&filter;	a=a>>>(nbits*3/4);		
		return new Color(r/max, g/max, b/max, 1.0f-a/max);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("Open an object")) opendialog();
		else if(ae.getActionCommand().equals("Up"))
		{
			if(ay<180) ay-=Math.PI*(Integer.parseInt(angle.getText())/180.0);
			else ay+=Math.PI*(Integer.parseInt(angle.getText())/180.0);
			if(az<180) az+=Math.PI*(Integer.parseInt(angle.getText())/180.0);
			else az-=Math.PI*(Integer.parseInt(angle.getText())/180.0);
			m=Math.cos(ay);
			n=Math.cos(az);			
			if(l==1.0) n=m=0; if(m==1.0) l=n=0; if(n==1.0) l=m=0; if(l==-1.0) n=m=0; if(m==-1.0) l=n=0; if(n==-1.0) l=m=0; 
			System.out.println("l="+l+", m="+m+", n="+n+", distance="+dist+", 1="+(l*l+m*m+n*n));		
			showview();
		}
		else if(ae.getActionCommand().equals("Down"))
		{
			if(ay<180) ay+=Math.PI*(Integer.parseInt(angle.getText())/180.0);
			else ay-=Math.PI*(Integer.parseInt(angle.getText())/180.0);
			if(az<180) az-=Math.PI*(Integer.parseInt(angle.getText())/180.0);
			else az-=Math.PI*(Integer.parseInt(angle.getText())/180.0);
			m=Math.cos(ay);
			n=Math.cos(az);						
			if(l==1.0) n=m=0; if(m==1.0) l=n=0; if(n==1.0) l=m=0; if(l==-1.0) n=m=0; if(m==-1.0) l=n=0; if(n==-1.0) l=m=0; 
			System.out.println("l="+l+", m="+m+", n="+n+", distance="+dist+", 1="+(l*l+m*m+n*n));
			showview();
		}
		else if(ae.getActionCommand().equals("Left"))
		{
			if(ax<180) ax+=Math.PI*(Integer.parseInt(angle.getText())/180.0);
			else ax-=Math.PI*(Integer.parseInt(angle.getText())/180.0);
			if(az<180) az-=Math.PI*(Integer.parseInt(angle.getText())/180.0);
			else az+=Math.PI*(Integer.parseInt(angle.getText())/180.0);
			l=Math.cos(ax);
			n=Math.cos(az);
			if(l==1.0) n=m=0; if(m==1.0) l=n=0; if(n==1.0) l=m=0; if(l==-1.0) n=m=0; if(m==-1.0) l=n=0; if(n==-1.0) l=m=0; 
			System.out.println("l="+l+", m="+m+", n="+n+", distance="+dist+", 1="+(l*l+m*m+n*n));
			showview();
		}
		else if(ae.getActionCommand().equals("Right"))
		{
			if(ax<180) ax-=Math.PI*(Integer.parseInt(angle.getText())/180.0);
			else ax+=Math.PI*(Integer.parseInt(angle.getText())/180.0);
			if(az<180) az+=Math.PI*(Integer.parseInt(angle.getText())/180.0);
			else az-=Math.PI*(Integer.parseInt(angle.getText())/180.0);
			m=Math.cos(ay);
			n=Math.cos(az);			
			if(l==1.0) n=m=0; if(m==1.0) l=n=0; if(n==1.0) l=m=0; if(l==-1.0) n=m=0; if(m==-1.0) l=n=0; if(n==-1.0) l=m=0; 
			System.out.println("l="+l+", m="+m+", n="+n+", distance="+dist+", 1="+(l*l+m*m+n*n));
			showview();
		}
		else if(ae.getActionCommand().equals("Move In"))
		{
			dist-=Integer.parseInt(distance.getText());
			System.out.println("l="+l+", m="+m+", n="+n+", distance="+dist+", 1="+(l*l+m*m+n*n));
			showview();
		}
		else if(ae.getActionCommand().equals("Move Out"))
		{
			dist+=Integer.parseInt(distance.getText());
			System.out.println("l="+l+", m="+m+", n="+n+", distance="+dist+", 1="+(l*l+m*m+n*n));
			showview();
		}
	}


}

