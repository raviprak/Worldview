/*	WORLDVIEW-Block	
 *Authored By : Ravi Prakash
 */
 
package data;

import java.io.*;

public class Block
{
	long arr[][];
	String desc;
	int x, y, z, nbits, len;
	final int dsize=64;
	
	void setDesc(String str)	//Set the descriptive string
	{
		desc=new String(str);
	}
	
	String getDesc()			//Get the descriptive string
	{
		return new String(desc);
	}
	
	point getSize()				//Returns the x,y,z dimensions as a point object 
	{
		point p=new point(x, y, z);		
		return p;
	}
	
	int getNbits()
	{
		return nbits;
	}

	public Block(int bits, int dx, int dy, int dz)	//Initialise the Block and reserve memory space
	{
		System.out.println("Block Constructor : Creating a block of size ("+dx+","+dy+","+dz+") with "+bits+" bits per cell");
		nbits=bits; x=dx; y=dy; z=dz; desc=" ";
		len=(int) Math.ceil((double)x*y*z*nbits/dsize);
		MemAlloc( (long)x*y*z*nbits );
	}
	
	public Block(int bits, point p)
	{		
		if(p==null) return;
		System.out.println("Block Constructor : Creating a block of size ("+p.x+","+p.y+","+p.z+") with "+bits+" bits per cell");
		nbits=bits; x=p.x; y=p.y; z=p.z; desc=" ";		
		len=(int) Math.ceil((double)x*y*z*nbits/dsize);
		MemAlloc( (long)x*y*z*nbits );
	}
	
	public Block(FileInputStream fin) throws IOException
	{
		readFromFile(fin);
	}
	
	void MemAlloc(long space)				//Allocate space bits to arr in rows of len
	{		
		try
		{
			arr=new long[(int)Math.ceil(((double)space/dsize)/len)][len];
			System.out.println("Allocated Memory : "+(space*8/dsize)+" bytes in rows of "+len);
		}
		catch(OutOfMemoryError e)
		{
			len=len/2;
			if(len>Math.sqrt(space)) MemAlloc(space);
			else System.out.println("Failed in allocating memory");		
		}		
	}

 	public void setData(long data, int posx, int posy, int posz)
	{
		long temp=((y*posz+posy)*x+posx)*nbits;				
		long stored=data;
		long temp1=arr[(int)((temp/dsize)/len)][((int)temp/dsize)%len];
		
		long filter=0;
		for(int i=0; i<nbits; i++)
		{
			filter=filter<<1; filter++;
		}
		
		filter=filter<<temp%dsize;
		data=data<<temp%dsize;
				
		filter=~filter;
		temp1=temp1&filter;
		temp1=temp1|data;
		arr[(int)((temp/dsize)/len)][((int)temp/dsize)%len]=temp1;
			
		if(temp%dsize>dsize-nbits)
		{			
			temp1=arr[(int) ((temp/dsize+1)/len) ][((int)(temp/dsize)+1)%len];
			filter=0;
			for(int i=0; i<nbits-dsize+temp%dsize; i++)
			{
				filter=filter<<1; filter++;
			}
			filter=~filter;
			temp1=temp1&filter;
			stored=stored>>>(dsize-temp%dsize);
			temp1=temp1|stored;		
			arr[(int) ((temp/dsize+1)/len) ][((int)(temp/dsize)+1)%len]=temp1;			
		}
	}
		
	public long getData(int posx, int posy, int posz)
	{
		long temp=((y*posz+posy)*x+posx)*nbits;		
		long temp1=arr[(int)((long)temp/dsize)/len][((int)temp/dsize)%len];
		long toret;
		
		long filter=0;
		for(int i=0; i<nbits; i++)
		{
			filter=filter<<1; filter++;
		}		
		
		filter=filter<<temp%dsize;		
		toret=temp1&filter;
		toret=toret>>>temp%dsize;		
		
		if(temp%dsize>dsize-nbits)
		{
			temp1=arr[(int) ((((long)temp/dsize)+1)/len) ][((int)(temp/dsize)+1)%len];
			filter=0;
			for(int i=0; i<nbits-dsize+temp%dsize; i++)
			{
				filter=filter<<1; filter++;
			}
			temp1=temp1&filter;			
			temp1=temp1<<(dsize-temp%dsize);
			toret=toret|temp1;
		}
		
		return toret;	
	}
	
	void writeToFile(FileOutputStream fout) throws IOException
	{
		byte str[];		
		str=desc.getBytes();
		util.writeInt(fout, str.length);
		for(int i=0; i<str.length; i++)
		{
			fout.write(str[i]);								
		}
		
		util.writeInt(fout, x);	util.writeInt(fout, y);	util.writeInt(fout, z);
		util.writeInt(fout, nbits);
		
		long filter, towrite;			
		for(int i=0; i<arr.length; i++)
		{
			for(int j=0; j<arr[i].length; j++)
			{
				filter=255;
				for(int k=0; k<8; k++)
					{
						towrite=arr[i][j]&filter;
						towrite=towrite>>>(k*8);
						fout.write((int)towrite);
						filter=filter<<8;							
					}
			}
		}
			
	}
	
	void readFromFile(FileInputStream fin) throws IOException
	{		
		byte str[]=new byte[util.readInt(fin)];
		for(int i=0; i<str.length; i++)
		{
			str[i]=(byte) fin.read();
		}
		desc=new String(str);
		x=util.readInt(fin); y=util.readInt(fin); z=util.readInt(fin);
		nbits=util.readInt(fin);
		
		len=(int) Math.ceil((double)x*y*z*nbits/dsize);
		MemAlloc( (long)x*y*z*nbits );
		
		long towrite;
		for(int i=0; i<arr.length; i++)
		{
			for(int j=0; j<arr[i].length; j++)
			{					
				for(int k=0; k<8; k++)
					{
						towrite=fin.read();
						towrite=towrite<<(k*8);
						arr[i][j]=arr[i][j]|towrite;				
					}
			}
		}
		
	}
	
	void replace(long toreplace, long with)
	{
		for(int i=0; i<x; i++)
			for(int j=0; j<y; j++)
				for(int k=0; k<0; k++)
					if(getData(i, j, k)==toreplace) setData(with, i, j, k); 
					
	}
	
}