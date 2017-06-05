package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MainMenu extends JFrame implements ActionListener
{
	Container cp;
	Aboutpage a;
	public MainMenu()
	{
		super("WorldView : Solid Modelling Software");		
		cp=getContentPane();
		cp.setLayout(new FlowLayout());
		ImageIcon frntpg=new ImageIcon("images/frntpg.gif");		
		JLabel fp=new JLabel(frntpg);
		cp.add(fp);		
		JButton rend=new JButton("Start Renderer");		
		cp.add(rend);
		JButton modl=new JButton("Start Modeller");		
		//add(modl);		
		JButton about=new JButton("About");		
		cp.add(about);
		rend.addActionListener(this);
		modl.addActionListener(this);
		about.addActionListener(this);
		//pack();
		setSize(frntpg.getIconWidth()+20,frntpg.getIconHeight()+80);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("Start Modeller"))	new Modeler.Modeler();
		if(ae.getActionCommand().equals("Start Renderer"))	new renderer.Renderer();
		if( (ae.getActionCommand()).equals("About") )
		{
			if(a==null) a=new Aboutpage();
			a.setVisible(true);
		}
	}

}


class Aboutpage extends JFrame
{
	Container cp;
	Aboutpage()
	{
		super("About Page");
		cp=getContentPane();
		StringBuffer toshow=new StringBuffer();
		try
		{
			FileReader fr=new FileReader("images\\about.txt");
			int read=fr.read();
			while(read!=-1)
			{
				toshow.append((char) read);
				read=fr.read();
			}
			fr.close();		
		}
		catch(FileNotFoundException fe)
		{	System.out.println("File Not Found : about.txt");	}
		catch(IOException ie)
		{	System.out.println("I/O Exception occured while reading : about.txt");	}
	
		JLabel thelabel=new JLabel(toshow.toString());
		thelabel.setSize(new Dimension(cp.getSize()));
		cp.add(thelabel);
		pack();
		setDefaultCloseOperation(HIDE_ON_CLOSE);				
	}
}
