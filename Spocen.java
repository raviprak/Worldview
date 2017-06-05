/*	WORLDVIEW-Spatial Occupancy Enumeration	
 *Authored By : Ravi Prakash
 *Tested By : 
 */
 
package data;

import java.io.*;

public class Spocen extends Thing
{
	final int blocksinthing=5;
	int x=0, y=0, z=0, count=0;
	Block blocks[]=new Block[blocksinthing];	
	int nbits[]=new int[blocksinthing];
	point parr[][]=new point[blocksinthing][2];
	double scale[][]=new double[blocksinthing][3];
	
	public void addBlock(Block toadd, point p1)
	{
		if(count==blocks.length-1)
		{
			Block temp[]=new Block[blocks.length+blocksinthing];
			int tempbits[]=new int[nbits.length+blocksinthing];			
			point temparr[][]=new point[parr.length+blocksinthing][2];
			double tempscal[][]=new double[scale.length+blocksinthing][3];
			for(int i=0; i<count; i++)
			{
				 temp[i]=blocks[i];
				 tempbits[i]=nbits[i];
				 temparr[i][0]=parr[i][0];	temparr[i][1]=parr[i][1];
				 tempscal[i][0]=scale[i][0];
				 tempscal[i][1]=scale[i][1];
				 tempscal[i][2]=scale[i][2];				 
			}
			blocks=temp;	nbits=tempbits;		parr=temparr;	scale=tempscal;
		}
		blocks[count]=toadd;
		nbits[count]=toadd.getNbits();
		parr[count][0]=new point(p1.x, p1.y, p1.z); parr[count][1]=new point(p1.x+toadd.x-1, p1.y+toadd.y-1, p1.z+toadd.z-1);
		if(parr[count][1].x>x) x=parr[count][1].x;
		if(parr[count][1].y>y) y=parr[count][1].y;
		if(parr[count][1].z>z) z=parr[count][1].z;
		scale[count][0]=scale[count][1]=scale[count][2]=1.0;		
		count++;
	}

	public void addBlock(Block toadd, point p1, point p2)
	{
		if(count==blocks.length-1)
		{
			Block temp[]=new Block[blocks.length+blocksinthing];
			int tempbits[]=new int[nbits.length+blocksinthing];
			point temparr[][]=new point[parr.length+blocksinthing][2];
			double tempscal[][]=new double[scale.length+blocksinthing][3];			
			for(int i=0; i<count; i++)
			{
				 temp[i]=blocks[i];
				 tempbits[i]=nbits[i];
				 temparr[i][0]=parr[i][0];	temparr[i][1]=parr[i][1];
				 tempscal[i][0]=scale[i][0];
				 tempscal[i][1]=scale[i][1];
				 tempscal[i][2]=scale[i][2];
			}	 				 
			blocks=temp;	nbits=tempbits;		parr=temparr;	scale=tempscal;			
		}		
		blocks[count]=toadd;
		nbits[count]=toadd.getNbits();
		parr[count][0]=new point(p1.x, p1.y, p1.z); parr[count][1]=new point(p2.x, p2.y, p2.z);
		if(parr[count][0].x>x) x=parr[count][0].x;		if(parr[count][0].y>y) y=parr[count][0].y;		if(parr[count][0].z>z) z=parr[count][0].z;		
		if(parr[count][1].x>x) x=parr[count][1].x;		if(parr[count][1].y>y) y=parr[count][1].y;		if(parr[count][1].z>z) z=parr[count][1].z;
		scale[count][0]=((double)(toadd.x)/(p2.x-p1.x+1));
		scale[count][1]=((double)(toadd.y)/(p2.y-p1.y+1));
		scale[count][2]=((double)(toadd.z)/(p2.z-p1.z+1));
		if(scale[count][0]<0) scale[count][0]*=-1;
		if(scale[count][1]<0) scale[count][1]*=-1;
		if(scale[count][2]<0) scale[count][2]*=-1;		
		count++;

	}

	public void addBlock(Block toadd, int x1, int y1, int z1, int x2, int y2, int z2)
	{
		if(count==blocks.length-1)
		{
			Block temp[]=new Block[blocks.length+blocksinthing];
			int tempbits[]=new int[nbits.length+blocksinthing];
			point temparr[][]=new point[parr.length+blocksinthing][2];
			double tempscal[][]=new double[scale.length+blocksinthing][3];			
			for(int i=0; i<count; i++)
			{
				 temp[i]=blocks[i];
				 tempbits[i]=nbits[i];
				 temparr[i][0]=parr[i][0];	temparr[i][1]=parr[i][1];
				 tempscal[i][0]=scale[i][0];
				 tempscal[i][1]=scale[i][1];
				 tempscal[i][2]=scale[i][2];
			}	 				 
			blocks=temp;	nbits=tempbits;		parr=temparr;	scale=tempscal;			
		}				
		blocks[count]=toadd;
		nbits[count]=toadd.getNbits();
		parr[count][0]=new point(x1, y1, z1); parr[count][1]=new point(x2, y2, z2);
		if(parr[count][0].x>x) x=parr[count][0].x;		if(parr[count][0].y>y) y=parr[count][0].y;		if(parr[count][0].z>z) z=parr[count][0].z;		
		if(parr[count][1].x>x) x=parr[count][1].x;		if(parr[count][1].y>y) y=parr[count][1].y;		if(parr[count][1].z>z) z=parr[count][1].z;		
		scale[count][0]=((double)(toadd.x)/(x2-x1+1));
		scale[count][1]=((double)(toadd.y)/(y2-y1+1));
		scale[count][2]=((double)(toadd.z)/(z2-z1+1));
		if(scale[count][0]<0) scale[count][0]*=-1;
		if(scale[count][1]<0) scale[count][1]*=-1;
		if(scale[count][2]<0) scale[count][2]*=-1;
		count++;
	}

	public void remBlock(int pos)
	{
		for(int i=pos; i<count-1; i++)
		{
			blocks[i]=blocks[i+1];
			parr[i]=parr[i+1];
			scale[i]=scale[i+1];			
		}
		count--;
	}
	
	public void remBlock(int x, int y, int z)
	{
		for(int i=0; i<count; i++)
		{
			if((parr[i][0].x<=x&&parr[i][1].x>=x)|(parr[i][1].x<=x&&parr[i][0].x>=x))
				if((parr[i][0].y<=y&&parr[i][1].y>=y)|(parr[i][1].y<=y&&parr[i][0].y>=y))
					if((parr[i][0].z<=z&&parr[i][1].z>=z)|(parr[i][1].z<=z&&parr[i][0].z>=z))
						{
							remBlock(i);
							return;
						}
		}
	}

	public void replace(long toreplace, long with)
	{
		for(int i=0; i<count; i++)
		{
			blocks[i].replace(toreplace, with);
		}
	}	

	public long[] getData(int x, int y, int z)
	{
		for(int i=0; i<count; i++)
		{
			if((parr[i][0].x<=x&&parr[i][1].x>=x)|(parr[i][1].x<=x&&parr[i][0].x>=x))
				if((parr[i][0].y<=y&&parr[i][1].y>=y)|(parr[i][1].y<=y&&parr[i][0].y>=y))
					if((parr[i][0].z<=z&&parr[i][1].z>=z)|(parr[i][1].z<=z&&parr[i][0].z>=z))
						{
							long toret[]={ (long) nbits[i] , blocks[i].getData( (int)scale[i][0]*(x-parr[i][0].x), (int)scale[i][1]*(y-parr[i][0].y), (int)scale[i][2]*(z-parr[i][0].z) ) };
							return toret;
						}
		}
		long toret[]={0l,0l};
		return toret;
	}

	public void setData(long data, int x, int y, int z)
	{
		for(int i=0; i<count; i++)
		{
			if((parr[i][0].x<=x&&parr[i][1].x>=x)|(parr[i][1].x<=x&&parr[i][0].x>=x))
				if((parr[i][0].y<=y&&parr[i][1].y>=y)|(parr[i][1].y<=y&&parr[i][0].y>=y))
					if((parr[i][0].z<=z&&parr[i][1].z>=z)|(parr[i][1].z<=z&&parr[i][0].z>=z))
						{
							blocks[i].setData(data, (int)scale[i][0]*(x-parr[i][0].x), (int)scale[i][1]*(y-parr[i][0].y), (int)scale[i][2]*(z-parr[i][0].z) );
							return;
						}		
		}
	}
	
	public void writeToFile(File fname)
	{
		try
		{
			FileOutputStream fout=new FileOutputStream(fname);
			byte str[];		
			str=desc.getBytes();
			util.writeInt(fout, str.length);
			for(int i=0; i<str.length; i++)	fout.write(str[i]);			
			util.writeInt(fout, count);
			for(int i=0; i<count; i++)
			{
				util.writeInt(fout, parr[i][0].x); util.writeInt(fout, parr[i][0].y); util.writeInt(fout, parr[i][0].z); 
				util.writeInt(fout, parr[i][1].x); util.writeInt(fout, parr[i][1].y); util.writeInt(fout, parr[i][1].z);
				blocks[i].writeToFile(fout);				
			}
			fout.close();
		}
		catch(FileNotFoundException f)
		{
			System.out.println("The file could not be opened for writing");
		}
		catch(IOException io)
		{
			System.out.println("There was an I/O error while writing to output stream");
		}
	}
	
	public void readFromFile(File fname)
	{
		try
		{
			FileInputStream fin=new FileInputStream(fname);
			byte str[]=new byte[util.readInt(fin)];
			for(int i=0; i<str.length; i++) str[i]=(byte) fin.read();
			desc=new String(str);
			int nblckstoread=util.readInt(fin);			
			for(int i=0; i<nblckstoread; i++)
			{				
				point p1=new point(); 
				point p2=new point();
				p1.x=util.readInt(fin); p1.y=util.readInt(fin); p1.z=util.readInt(fin);
				p2.x=util.readInt(fin); p2.y=util.readInt(fin); p2.z=util.readInt(fin);			
				Block b=new Block(fin);
				addBlock(b, p1, p2);				
			}			
			fin.close();
			System.out.println("Read "+desc+" from "+fname);
		}
		catch(FileNotFoundException f)
		{
			System.out.println("Read operation failed. File could not be opened.");
		}
		catch(IOException ie)
		{
			System.out.println("An I/O exception occured while reading. Aborting."+ie);
		}
	}
	
	public point getDimension()
	{
		point p1=new point(x, y, z);
		return p1;
	}
	
	boolean isInside(int px, int py, int pz)
	{	
		if( px<x && py<y && pz<z )	return true;
		return false;
	}	

	public int getCount()
	{
		return count;
	}
	
	public String getBlockDesc(int i)
	{
		return new String(blocks[i].getDesc());
	}
	
	public point[] getPoints(int i)
	{
		point toret[]=new point[2];
		toret[0]=new point(parr[i][0].x, parr[i][0].y, parr[i][0].z);
		toret[1]=new point(parr[i][1].x, parr[i][1].y, parr[i][1].z);
		return toret;
	}
	
	public point getBlockDim(int i)
	{
		return blocks[i].getSize();
	}
	
}

