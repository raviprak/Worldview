import java.awt.*;
import java.awt.image.*;
import javax.swing.*;


public class test extends JFrame
{
	BufferedImage view;
	Graphics pntr;
	Container cp;
	double l, m, n;
	JLabel toshow;

	public test()
	{
		view=new BufferedImage(500, 500, BufferedImage.TYPE_4BYTE_ABGR);
		pntr=view.getGraphics();
		pntr.setColor(Color.BLACK);
		cp=getContentPane();
		l=Math.cos(Math.PI/4); m=Math.cos(Math.PI/4); n=Math.cos(Math.PI/4);
		setMembers();
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	void setMembers()
	{
		cp.setLayout(new FlowLayout());
		toshow=new JLabel();
		cp.add(toshow);		
		for(int i=-100; i<100; i++)
		{
			for(int j=-100; j<100; j++)
			{
				for(int k=-100; k<100; k++)
				{
					if((int) l*i+m*j+n*k==0)
					drawData(2l, i, j, k);
				}
			}
		}
		ImageIcon temp=new ImageIcon(view);
		toshow.setIcon(temp);
	}
	
	void drawData(long data, int x, int y, int z)
	{		
		double x1, y1;
		y1=(Math.sqrt(x*x + y*y + z*z)*y)/Math.sqrt(x*x+y*y);
		x1=y1*x/y;
		//System.out.print("("+(int)x1+","+(int)y1+") ");
		pntr.drawRect(200+(int)(x*m+ z*Math.sin(Math.acos(m))) , 200+(int)(y*l- Math.sin(Math.acos(l)) *(-1*x*Math.sin(Math.acos(m))+z*m)), 1,1);
		System.out.print((int)(y*Math.sin(Math.acos(l))-x*l*Math.sin(Math.acos(m))+z*l*m)+" ");
		//pntr.drawRect(150+(int)(n*x-(Math.sin(Math.acos(n))*y)), 150+(int)(Math.sin(Math.acos(n))*x+n*y), 1,1);
		//pntr.drawRect( (int)(0.5*view.getWidth()+(x+z*(Math.sin(Math.acos(m))+l))*mult), (int)((y+z*(Math.sin(Math.acos(l))+m))*mult+0.5*view.getWidth()), (int) mult, (int) mult);
		//pntr.drawRect( (int)(0.5*view.getWidth()+(x+z*(l/n))*mult), (int)((y+z*(m/n))*mult+0.5*view.getWidth()), 1,1);	
		//pntr.drawRect( 150+(int)(x1), 150+(int)(y1), 1, 1);
	}
	
	public static void main(String arg[])
	{
		new test();
	}
}