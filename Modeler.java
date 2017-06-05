package Modeler;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import data.*;

public class Modeler extends JFrame implements ActionListener
{
	Container cp;
	Spocen thethng;
	JFileChooser fc;
	JScrollPane scrlpn;

	
	public Modeler()
	{
		super("Worldview Spatial Occupancy Enumeration Solid Modeler");
		cp=getContentPane();
		fc=new JFileChooser(new File("back"));
		setupMenu();
		setupMembers();
		setVisible(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
	
	void setupMembers()
	{
		cp.setLayout(new FlowLayout());
		JButton addblck=new JButton("Add Block", new ImageIcon("images/addblck.gif"));
		addblck.addActionListener(new modifier(this));
		cp.add(addblck);
		scrlpn=new JScrollPane();
		cp.add(scrlpn);
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
		open.addActionListener(this);
		JMenuItem save=new JMenuItem("Save this object", new ImageIcon("images/save.gif"));
		save.setMnemonic(KeyEvent.VK_S);
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
		open.addActionListener(this);		
		file.add(open);
		file.add(save);		
		bar.add(file);
		JMenu modify=new JMenu("Modify");
		modify.setMnemonic(KeyEvent.VK_M);
		JMenuItem addblck=new JMenuItem("Add a block", new ImageIcon("images/addblck.gif"));
		addblck.setMnemonic(KeyEvent.VK_A);
		addblck.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));
		addblck.addActionListener(new modifier(this));
		JMenuItem remblck=new JMenuItem("Remove a block", new ImageIcon("images/remblck.gif"));
		addblck.setMnemonic(KeyEvent.VK_R);
		addblck.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.CTRL_MASK));
		addblck.addActionListener(new modifier(this));
		JMenuItem replace=new JMenuItem("Replace data", new ImageIcon("images/replace.gif"));
		addblck.setMnemonic(KeyEvent.VK_C);
		addblck.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));
		addblck.addActionListener(new modifier(this));
		modify.add(addblck);
		modify.add(remblck);
		modify.add(replace);
		bar.add(modify);		
		setJMenuBar(bar);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("Open an object"))
		{
			int ret=fc.showOpenDialog(this);
			if(ret==JFileChooser.APPROVE_OPTION)
			{
				File toopen=fc.getSelectedFile();
				thethng=new Spocen();
				thethng.readFromFile(toopen);				
			}			
			else return;
			Object clmns[]={"Block No.", "Block desc", "Point p1", "Point p2", "Dimensions (x,y,z)", "Edit this block", "Remove this block"};
			Object data[][]=new Object[thethng.getCount()][clmns.length];
			for(int i=0; i<data.length; i++)
			{
				data[i][0]=""+i;	data[i][1]=thethng.getBlockDesc(i);	point p[]=thethng.getPoints(i); 
				data[i][2]="("+p[0].x+","+p[0].y+","+p[0].z+")"; data[i][3]="("+p[1].x+","+p[1].y+","+p[1].z+")";
				point p1=thethng.getBlockDim(i); data[i][4]="("+p1.x+","+p1.y+","+p1.z+")";				
			}
			JTable tbl=new JTable(data, clmns);
			for(int i=0; i<data.length; i++)
			{
				JButton edit=new JButton("Edit");	edit.setActionCommand("Edit "+i);				
				tbl.setValueAt(edit, i, 5);
				JButton rem=new JButton("Remove");	rem.setActionCommand("Remove "+i);
				tbl.setValueAt(rem, i, 6);
			}
			
			scrlpn.setViewportView(tbl);		
		}
		if(ae.getActionCommand().equals("Save this object"))
		{
			int ret=fc.showSaveDialog(this);
			if(ret==JFileChooser.APPROVE_OPTION)
			{
				File toopen=fc.getSelectedFile();
				thethng.writeToFile(toopen);
			}
			else return;
		}
		
	}

			
}

class modifier implements ActionListener
{
	Modeler modl;
	modifier(Modeler m)
	{
		modl=m;
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("Add Block")|ae.getActionCommand().equals("Add a block"))
		{
			BlockModeler b=new BlockModeler();
			//modl.thethng.addBlock(b.getBlock());
		}
		
		if(ae.getActionCommand().equals("Remove a block"))
		{
			new blckremover(modl);
		}
		if(ae.getActionCommand().equals("Replace data"))
		{
			new replacer(modl);
		}
	}
}

class blckremover extends JFrame implements ActionListener
{
	Container cp;
	Modeler modl;
	JTextArea x, y, z;
	blckremover(Modeler m)
	{
		cp=getContentPane();
		modl=m;
		x=new JTextArea(1,5);
		y=new JTextArea(1,5);
		z=new JTextArea(1,5);
		cp.add(x); cp.add(y); cp.add(z); 
		JButton remove=new JButton("Remove block", new ImageIcon("images/remblck.gif"));
		remove.addActionListener(this);
		cp.add(remove);
		pack();
		setVisible(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("Remove Block"))
		{
			modl.thethng.remBlock(Integer.parseInt(x.getText()), Integer.parseInt(y.getText()), Integer.parseInt(z.getText()));
		}
	}
}

class replacer extends JFrame implements ActionListener
{
	Container cp;
	Modeler modl;
	JTextArea toreplace, with;
	replacer(Modeler m)
	{
		cp=getContentPane();
		modl=m;
		toreplace=new JTextArea(1,5);		
		with=new JTextArea(1,5);
		cp.add(toreplace); cp.add(with);
		JButton replace=new JButton("Replace data", new ImageIcon("images/replace.gif"));
		replace.addActionListener(this);
		cp.add(replace);
		pack();
		setVisible(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("Replace data"))
		{
			modl.thethng.replace(Integer.parseInt(toreplace.getText()), Integer.parseInt(with.getText()));
		}
	}
}
